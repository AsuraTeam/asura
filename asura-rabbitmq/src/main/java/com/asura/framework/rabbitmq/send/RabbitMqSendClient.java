/**
 * @FileName: RabbitSendClient.java
 * @Package com.asure.framework.rabbitmq.send
 * @author zhangshaobin
 * @created 2016年2月29日 下午9:50:55
 * <p/>
 * Copyright 2011-2015 asura
 */
package com.asura.framework.rabbitmq.send;

import com.asura.framework.rabbitmq.MessageMethod;
import com.asura.framework.rabbitmq.PublishSubscribeType;
import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.asura.framework.rabbitmq.entity.ExchangeName;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.entity.RabbitMessage;
import com.asura.framework.rabbitmq.entity.RoutingKey;
import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * rabbitmq消息生产端
 * 根据factory创建链接
 * 根据链接创建channel
 * </p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhangshaobin
 * @version 1.0
 * @since 1.0
 */
public class RabbitMqSendClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitMqSendClient.class);

    private RabbitConnectionFactory rabbitConnectionFactory;

    private volatile Channel channel;

    private String environment;

    /**
     * @return the rabbitConnectionFactory
     */
    public RabbitConnectionFactory getRabbitConnectionFactory() {
        return rabbitConnectionFactory;
    }


    /**
     * @param rabbitConnectionFactory the rabbitConnectionFactory to set
     */
    public void setRabbitConnectionFactory(RabbitConnectionFactory rabbitConnectionFactory) throws Exception {
        this.rabbitConnectionFactory = rabbitConnectionFactory;
        this.environment = rabbitConnectionFactory.getEnvironment();
    }

    /**
     * 发送消息-queue方式
     *
     * @param queueName 格式为：系统标示_模块标示_功能标示
     * @param msg       具体消息
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:39:23
     */
    public void sendQueue(QueueName queueName, String msg) throws Exception {
        RabbitMessage rm = new RabbitMessage();
        rm.setData(msg);
        rm.setType(queueName.getNameByEnvironment(environment));
        sendQueue(queueName, rm);
    }

    /**
     * 发送消息-queue方式
     *
     * @param queueName 格式为：系统标示_模块标示_功能标示
     * @param msg       具体消息
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:39:23
     */
    public void sendQueue(QueueName queueName, String msg, MessageMethod messageMethod) throws Exception {
        if (msg == null || queueName == null) {
            return;
        }
        RabbitMessage rm = new RabbitMessage();
        rm.setData(msg);
        rm.setType(queueName.getNameByEnvironment(environment));
        rm.setMethod(messageMethod.getName());
        sendQueue(queueName, rm);
    }

    private void sendQueue(QueueName queueName, RabbitMessage rm) throws Exception {
        if (rm == null || queueName == null) {
            return;
        }
        String _queueName = queueName.getNameByEnvironment(environment);
        Transaction trans = Cat.newTransaction("RabbitMQ Message", "PUBLISH-QUEUE-" + _queueName);
        try {
            createChannel();
            Cat.logEvent("mq send queue", _queueName, Event.SUCCESS, rm.toJsonStr());
            channel.queueDeclare(_queueName, true, false, false, null);
            channel.basicPublish("", _queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, rm.toJsonStr().getBytes("UTF-8"));
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("SEND SUCCESS:[queue:{},message:{}]", _queueName, rm.toJsonStr());
            }
            Cat.logMetricForCount("PUBLISH-QUEUE-" + _queueName); // 统计请求次数, 可以查看对应队列中放入了多少信息
            trans.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("SEND ERROR:[queue:{},message:{},exception:{}]", _queueName, rm.toJsonStr(), e);
            }
            trans.setStatus(e);
            handleException(_queueName, e);
        } finally {
            trans.complete();
        }
    }

    /**
     * 发送消息-topic方式
     *
     * @param exchangeName 格式为：系统标示_模块标示_功能标示
     * @param msg          具体消息
     * @author zhangshaobin
     * @created 2016年3月1日 下午4:40:59
     */
    public void sendTopic(ExchangeName exchangeName, RoutingKey routingKey, PublishSubscribeType type, String msg) throws Exception {
        String _exchange = exchangeName.getNameByEnvironment(environment);
        Transaction trans = Cat.newTransaction("RabbitMQ Message", "PUBLISH-TOPIC-" + _exchange);
        RabbitMessage rm = new RabbitMessage();
        rm.setData(msg);
        rm.setType(_exchange);
        try {
            createChannel();
            Cat.logEvent("mq send topic", exchangeName.getName(), Event.SUCCESS, rm.toJsonStr());
            rm.setType(_exchange);
            channel.exchangeDeclare(_exchange, type.getName(), true);
            channel.basicPublish(_exchange, routingKey.getKey(), null, rm.toJsonStr().getBytes("UTF-8"));
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("SEND SUCCESS:[exchange:{},message:{}]", _exchange, rm.toJsonStr());
            }
            Cat.logMetricForCount("PUBLISH-TOPIC-" + _exchange); // 统计请求次数, 可以查看对应队列中放入了多少信息
            trans.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("SEND ERROR:[exchange:{},message:{},exception:{}]", _exchange, rm, e);
            }
            trans.setStatus(e);
            handleException(_exchange, e);
        } finally {
            trans.complete();
        }
    }

    private void handleException(String name, Exception e) throws Exception {
        String err = name + "rabbitmq发送消息异常";
        Cat.logError(err, e);
        closeChannel();
        throw new AsuraRabbitMqException(err, e);
    }

    /**
     * 单例的模式，新增双重锁检查
     *
     * @return
     * @throws Exception
     */
    private void createChannel() throws Exception {
        if (channel == null || !channel.isOpen()) {
            synchronized (this) {
                if (channel == null || !channel.isOpen()) {
                    Connection connection = rabbitConnectionFactory.getConnection();
                    channel = connection.createChannel();
                }
            }
        }
    }

    /**
     *
     */
    private void closeChannel() throws Exception {
        if (channel != null && channel.isOpen()) {
            channel.close();
            channel.getConnection().close();
        }
    }


}
