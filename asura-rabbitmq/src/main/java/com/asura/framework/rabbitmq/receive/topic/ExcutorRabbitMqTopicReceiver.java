/**
 * @FileName: ExcutorRabbitMqTopicReceiver.java
 * @Package: com.asura.framework.rabbitmq.receive
 * @author sence
 * @created 3/9/2016 7:31 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.asura.framework.rabbitmq.receive.topic;

import com.asura.framework.rabbitmq.PublishSubscribeType;
import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.asura.framework.rabbitmq.entity.BindingKey;
import com.asura.framework.rabbitmq.entity.ExchangeName;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;
import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>多线程消费</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class ExcutorRabbitMqTopicReceiver extends AbstractRabbitMqTopicReceiver {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcutorRabbitMqTopicReceiver.class);
    private static final int DEFAULT_POOL_SIZE = 1;
    /**
     *
     */
    private ExecutorService executorService;

    private int poolSize;

    public ExcutorRabbitMqTopicReceiver() {
        this.poolSize = DEFAULT_POOL_SIZE;
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        QueueName queueName, BindingKey bindingKey, ExchangeName exchangeName) {
        this(rabbitConnectionFactory, rabbitMqMessageLiteners, queueName, bindingKey, exchangeName, PublishSubscribeType.DIRECT, 1);
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        QueueName queueName, BindingKey bindingKey, ExchangeName exchangeName, int poolSize) {
        this(rabbitConnectionFactory, rabbitMqMessageLiteners, queueName, bindingKey, exchangeName, PublishSubscribeType.DIRECT, poolSize);
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        BindingKey bindingKey, ExchangeName exchangeName) {
        this(rabbitConnectionFactory, rabbitMqMessageLiteners, null, bindingKey, exchangeName, PublishSubscribeType.DIRECT, 1);
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        BindingKey bindingKey, ExchangeName exchangeName, int poolSize) {
        this(rabbitConnectionFactory, rabbitMqMessageLiteners, null, bindingKey, exchangeName, PublishSubscribeType.DIRECT, poolSize);
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        BindingKey bindingKey, ExchangeName exchangeName, PublishSubscribeType publishSubscribeType, int poolSize) {
        this(rabbitConnectionFactory, rabbitMqMessageLiteners, null, bindingKey, exchangeName, publishSubscribeType, poolSize);
    }

    public ExcutorRabbitMqTopicReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners,
                                        QueueName queueName, BindingKey bindingKey, ExchangeName exchangeName, PublishSubscribeType publishSubscribeType, int poolSize) {
        super(rabbitConnectionFactory, rabbitMqMessageLiteners, queueName, bindingKey, exchangeName, publishSubscribeType);
        this.poolSize = poolSize;
    }

    @Override
    public void doConsumeTopicMessage(Connection connection, String environment) throws IOException, InterruptedException {
        executorService = Executors.newFixedThreadPool(poolSize);
        for (int size = 0; size < this.getPoolSize(); size++) {
            TopicConsumerWorker consumerWorker = new TopicConsumerWorker(getiReceiveFailover(), connection, environment, getExchangeName(), getQueueName(), getBindingKey(), getPublishSubscribeType().getName(), getRabbitMqMessageLiteners(), getErrorHandler());
            executorService.submit(consumerWorker);
        }
        //优雅停机
        Runtime.getRuntime().addShutdownHook(new Thread(() -> destroyResource()));
    }

    @Override
    public void destroyResource() throws AsuraRabbitMqException {
        try {
            this.executorService.shutdown();
            this.executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
            super.destroyResource();
        } catch (InterruptedException e) {
            LOGGER.warn("destroy error ", e);
            throw new AsuraRabbitMqException(e.getMessage());
        }
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

}
