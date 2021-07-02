package com.test.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.test.rabbitmq.util.RabbitMqUtils;

import java.util.Scanner;

public class Emitlog {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
//        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("生产者发送成功" + message);
        }
    }
}
