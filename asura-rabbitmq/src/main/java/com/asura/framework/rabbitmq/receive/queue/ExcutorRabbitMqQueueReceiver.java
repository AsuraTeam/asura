/**
 * @FileName: ExcutorRabbitMqQueueRceiver.java
 * @Package: com.asura.framework.rabbitmq.receive
 * @author sence
 * @created 3/9/2016 7:15 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.asura.framework.rabbitmq.receive.queue;

import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;
import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p></p>
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
public class ExcutorRabbitMqQueueReceiver extends AbstractRabbitQueueReceiver {


    private final static Logger LOGGER = LoggerFactory.getLogger(ExcutorRabbitMqQueueReceiver.class);
    private static final int DEFAULT_POOL_SIZE = 1;

    private ExecutorService executorService;

    private int poolSize;

    public ExcutorRabbitMqQueueReceiver() {
        this.poolSize = DEFAULT_POOL_SIZE;
    }

    public ExcutorRabbitMqQueueReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners, QueueName queueName) {
        this(rabbitConnectionFactory, rabbitMqMessageLiteners, 1, queueName);
    }

    public ExcutorRabbitMqQueueReceiver(RabbitConnectionFactory rabbitConnectionFactory, List<IRabbitMqMessageLisenter> rabbitMqMessageLiteners, int poolSize, QueueName queueName) {
        super(rabbitConnectionFactory, rabbitMqMessageLiteners, queueName);
        this.poolSize = poolSize;

    }

    @Override
    public void doConsumeQueueMessage(Connection connection, String environment) {
        executorService = Executors.newFixedThreadPool(poolSize);
        for (int i = 0; i < poolSize; i++) {
            QueueConsumerWorker queueConsumerWorker = new QueueConsumerWorker(this.getiReceiveFailover(), this.getQueueName(), connection, environment, this.getRabbitMqMessageLiteners(), getErrorHandler());
            executorService.submit(queueConsumerWorker);
        }
        //优雅停机
        Runtime.getRuntime().addShutdownHook(new Thread(() -> destroyResource()));
    }


    @Override
    public void destroyResource() throws AsuraRabbitMqException {
        try {
            LOGGER.info("shutdown ...");
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
