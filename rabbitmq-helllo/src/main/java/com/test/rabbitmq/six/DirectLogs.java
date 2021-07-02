package com.test.rabbitmq.six;

import com.rabbitmq.client.Channel;
import com.test.rabbitmq.util.RabbitMqUtils;

import java.util.Scanner;

public class DirectLogs {
    public static final String EXCHANGE_NAME = "direct_logs2";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
//        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"error",null,message.getBytes());
            System.out.println("生产者发送成功" + message);
        }
    }
}
