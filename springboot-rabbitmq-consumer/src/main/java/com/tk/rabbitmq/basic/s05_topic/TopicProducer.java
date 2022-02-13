package com.tk.rabbitmq.basic.s05_topic;

import java.util.Random;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TopicProducer {
    private static final String EXCHANGE_NAME = "topic-sample";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq.study.com");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("123456");

        Random random = new Random();
        String[] speeds = {"higher", "middle", "lazy"};
        String[] colours = {"red", "orange", "blue", "black", "yellow", "green"};
        String[] species = {"pig", "rabbit", "monkey", "dog", "cat"};

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            // 创建topic交换器
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey = null;
            for (int i = 0; i < 100; i++) {

                routingKey = speeds[random.nextInt(100) % speeds.length] + "."
                        + colours[random.nextInt(100) % colours.length] + "."
                        + species[random.nextInt(100) % species.length];

                String message = "message task-" + i + " routingKey=" + routingKey;
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                System.out.println("发送消息：" + message);
                Thread.sleep(3000L);
            }

        }
    }
}
