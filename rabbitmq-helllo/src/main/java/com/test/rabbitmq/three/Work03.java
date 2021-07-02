package com.test.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.test.rabbitmq.util.RabbitMqUtils;
import com.test.rabbitmq.util.SleepUtils;

public class Work03 {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("c1等待处理时间较短");
        DeliverCallback deliverCallback = (a,b) ->{
            SleepUtils.sleep(1);
            System.out.println("接受到的消息" + new String(b.getBody()));
            channel.basicAck(b.getEnvelope().getDeliveryTag(),false);
        };
        channel.basicQos(2);
        channel.basicConsume(TASK_QUEUE_NAME, false,deliverCallback,(consumerTag -> {
            System.out.println("消息回调");
        }));
    }
}
