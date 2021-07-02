package com.test.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.test.rabbitmq.util.RabbitMqUtils;

public class ReceiveLogs01 {

    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 创建一个队列
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,EXCHANGE_NAME , "");
        System.out.println("等待接受消息");
        DeliverCallback deliverCallback = (consumer,message) ->{
            System.out.println("接受到的消息"+ new String(message.getBody()));
        };
        channel.basicConsume(queue, true,deliverCallback,consumerTag -> {});
    }
}
