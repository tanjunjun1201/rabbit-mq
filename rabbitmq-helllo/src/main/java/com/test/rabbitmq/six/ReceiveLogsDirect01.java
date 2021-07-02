package com.test.rabbitmq.six;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.test.rabbitmq.util.RabbitMqUtils;

public class ReceiveLogsDirect01 {
    public static final String EXCHANGE_NAME = "direct_logs2";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        channel.queueDeclare("console", false, false, false, null);

        channel.queueBind("console",EXCHANGE_NAME ,"info" );
        channel.queueBind("console",EXCHANGE_NAME ,"warning" );
        DeliverCallback deliverCallback = (consumer, message) ->{
            System.out.println("01接受到的消息"+ new String(message.getBody()));
        };
        channel.basicConsume("console", true,deliverCallback,consumerTag -> {});
    }
}
