package com.test.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 */
public class Consumer {
    public static final java.lang.String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.129.155");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        DeliverCallback deliverCallback = (consumerTag,message) ->{
            System.out.println(new String(message.getBody()));
        };

        CancelCallback cancelCallback = consumerTag ->{
            System.out.println("消息消费被中断");
        };


        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
