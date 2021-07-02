package com.test.rabbitmq.six;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.test.rabbitmq.util.RabbitMqUtils;

public class ReceiveLogsDirect02 {
    public static final String EXCHANGE_NAME = "direct_logs2";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        channel.queueDeclare("disk", false, false, false, null);

        channel.queueBind("disk",EXCHANGE_NAME ,"error" );
        DeliverCallback deliverCallback = (consumer, message) ->{
            System.out.println("02接受到的消息"+ new String(message.getBody()));
        };
        channel.basicConsume("disk", true,deliverCallback,consumerTag -> {});
    }
}
