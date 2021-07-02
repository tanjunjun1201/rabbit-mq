package com.test.rabbitmq.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.test.rabbitmq.util.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作线程 消费者
 */
public class Worker01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback = (a,b) ->{
            System.out.println("接受到的消息"+new String(b.getBody()));
        };

        CancelCallback cancelCallback = (a) ->{
            System.out.println("消息被取消掉"+ a);
        };
        System.out.println("C2等待接受消息。。。。。");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
