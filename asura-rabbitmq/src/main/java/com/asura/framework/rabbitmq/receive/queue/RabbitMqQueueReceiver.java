/**
 * @FileName: SampleRabbitMqReceiver.java
 * @Package: com.asura.framework.rabbitmq.receive
 * @author sence
 * @created 3/9/2016 6:23 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.asura.framework.rabbitmq.receive.queue;

import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.List;

/**
 * <p>队列消息接受</p>
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
public class RabbitMqQueueReceiver extends AbstractRabbitQueueReceiver {


    /**
     *
     */
    public RabbitMqQueueReceiver() {

    }

    /**
     * @param rabbitConnectionFactory
     * @param rabbitMqMessageLiteners
     * @param queueName
     */
    public RabbitMqQueueReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners, QueueName queueName) {
        super(rabbitConnectionFactory, rabbitMqMessageLiteners, queueName);
    }


    /**
     * 1个线程消费
     *
     * @param connection
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void doConsumeQueueMessage(Connection connection, String environment) throws IOException, InterruptedException {
        QueueConsumerWorker queueConsumerWorker = new QueueConsumerWorker(this.getiReceiveFailover(), this.getQueueName(), connection, environment, this.getRabbitMqMessageLiteners(), getErrorHandler());
        Thread worker = new Thread(queueConsumerWorker);
        worker.start();
        //优雅停机，添加接收到shutdownhook设置线程为中断状态
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //中断线程
            worker.interrupt();
            destroyResource();
        }));
    }

}
