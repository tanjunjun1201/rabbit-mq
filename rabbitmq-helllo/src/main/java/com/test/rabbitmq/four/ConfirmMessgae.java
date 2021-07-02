package com.test.rabbitmq.four;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.test.rabbitmq.util.RabbitMqUtils;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConfirmMessgae {

    public static final int MESSAGE_COUNT= 1000;

    public static void main(String[] args) throws Exception {
        publishMessageSingle();
    }

    public static void publishMessageSingle() throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        String s = UUID.randomUUID().toString();
        channel.queueDeclare(s,false,false,false,null);
        channel.confirmSelect();
        // 并发线程
        ConcurrentSkipListMap<Long,String> concurrentSkipListMap = new ConcurrentSkipListMap<>();

        // 成功
        ConfirmCallback askCallback = (deliveryTag,multiple) ->{
            if (multiple) {
                ConcurrentNavigableMap<Long, String> longStringConcurrentNavigableMap = concurrentSkipListMap.headMap(deliveryTag);
                longStringConcurrentNavigableMap.clear();
            } else {
                concurrentSkipListMap.remove(deliveryTag);
            }

            System.out.println("确认的消息" + deliveryTag);
        };
        // 失败
        ConfirmCallback nackCallback = (deliveryTag,multiple) ->{
            String s1 = concurrentSkipListMap.get(deliveryTag);
            System.out.println("未确认的消息" + s1);
        };
        channel.addConfirmListener(askCallback,nackCallback);
        long l = System.currentTimeMillis();
        for (int messageCount = MESSAGE_COUNT; messageCount > 0; messageCount--) {
            channel.basicPublish("", s + MESSAGE_COUNT, null, s.getBytes());
            concurrentSkipListMap.put(channel.getNextPublishSeqNo(),s);
        }

        long e = System.currentTimeMillis();
        System.out.println("发送时长"+ (e - l));  // 339   // 44
    }
}
