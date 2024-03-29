package com.tk.rabbitmq.basic.s10_exclusive_consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 简单队列生产者 使用RabbitMQ的默认交换器发送消息<br>
 * producer --> Queue --> 多个Consumer
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、设置连接属性
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        try (
                // 3、从连接工厂获取连接
                Connection connection = factory.newConnection("生产者");
                // 4、从链接中创建通道
                Channel channel = connection.createChannel();) {

            for (int i = 0; i < 100; i++) {
                // 消息内容
                String message = "message task" + i;
                // 6、发送消息
                channel.basicPublish("", "queue1", null, message.getBytes());
                System.out.println("发送消息：" + message);
                Thread.sleep(1000L);
            }

        }
    }
}
