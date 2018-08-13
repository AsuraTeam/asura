/**
 * @FileName: TopicConsumerWorker.java
 * @Package: com.asura.framework.rabbitmq.receive.topic
 * @author liusq23
 * @created 2017/4/21 下午6:00
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.framework.rabbitmq.receive.topic;

import com.asura.framework.rabbitmq.entity.BindingKey;
import com.asura.framework.rabbitmq.entity.ExchangeName;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;
import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.asura.framework.rabbitmq.receive.errorHandler.IErrorHandler;
import com.asura.framework.rabbitmq.receive.failover.IReceiveFailover;
import com.asura.framework.rabbitmq.receive.queue.RabbitMqQueueReceiver;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public class TopicConsumerWorker implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitMqQueueReceiver.class);

    private QueueName queueName;

    private Connection connection;

    private ExchangeName exchangeName;

    private BindingKey bindingKey;

    private String routingType;

    private String environment;

    private List<IRabbitMqMessageLisenter> lisenters;

    private IReceiveFailover iReceiveFailover;

    private IErrorHandler errorHandler;

    public TopicConsumerWorker(IReceiveFailover iReceiveFailover, Connection connection,
                               String environment, ExchangeName exchangeName, QueueName queueName,
                               BindingKey bindingKey, String routingType, List<IRabbitMqMessageLisenter> lisenters,
                               IErrorHandler errorHandler) {
        this.bindingKey = bindingKey;
        this.exchangeName = exchangeName;
        this.routingType = routingType;
        this.connection = connection;
        this.lisenters = lisenters;
        this.queueName = queueName;
        this.environment = environment;
        this.iReceiveFailover = iReceiveFailover;
        this.errorHandler = errorHandler;
    }

    @Override
    public void run() {
        Channel channel = null;
        try {
            channel = connection.createChannel();
            String _exchangeName = exchangeName.getNameByEnvironment(environment);
            channel.exchangeDeclare(_exchangeName, routingType, true);
            String qname = parseQueueName(channel);
            //申明接收topic的queue
            channel.queueDeclare(qname, true, false, false, null);
            //申明队列绑定的路由key
            channel.queueBind(qname, _exchangeName, bindingKey.getKey());
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(qname, false, consumer);
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                Transaction trans = Cat.newTransaction("RabbitMQ Message", "CONSUME-TOPIC-" + _exchangeName);
                String message = new String(delivery.getBody(), "UTF-8");
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("CONSUMER TOPIC MESSAGE:[exchange:{},queue:{},bindingKey:{},message:{}]", _exchangeName, qname, bindingKey.getKey(), message);
                }
                Cat.logEvent("mq consumer exchange", _exchangeName, Event.SUCCESS, bindingKey.getKey() + ":" + message);
                Cat.logMetricForCount("CONSUME-TOPIC-" + _exchangeName);
                try {
                    for (IRabbitMqMessageLisenter lisenter : lisenters) {
                        lisenter.processMessage(delivery);
                    }
                    trans.setStatus(Transaction.SUCCESS);
                } catch (Exception e) {
                    Cat.logError("队列[" + _exchangeName + "," + qname + "," + bindingKey.getKey() + "]消费异常", e);
                    if (LOGGER.isErrorEnabled()) {
                        LOGGER.error("CONSUMER TOPIC MESSAGE:[exchange:{},queue:{},bindingKey:{},message:{}]", _exchangeName, qname, bindingKey.getKey(), message);
                    }
                    if (errorHandler != null) {
                        errorHandler.handlerTopicConsumeError(exchangeName.getName(), qname, bindingKey.getKey(), message, e);
                    }
                    trans.setStatus(e);
                } finally {
                    trans.complete();
                }
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("rabbmitmq consumer error:", e);
            }
            if (e instanceof AsuraRabbitMqException) {

            } else {
                iReceiveFailover.doFailover();
            }
        }

    }

    /**
     * 解析队列名字，如果没有，则认为使用的是临时队列
     *
     * @param channel
     * @return
     * @throws IOException
     */
    private String parseQueueName(Channel channel) throws IOException {
        String qname;
        if (queueName == null) {
            qname = channel.queueDeclare().getQueue();
        } else {
            qname = queueName.getNameByEnvironment(environment);

        }
        return qname;
    }
}