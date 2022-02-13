package com.tk.rabbitmq.basic.s01_helloworld;

import java.io.IOException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

/**
 * 简单队列消费者
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、设置连接属性
        factory.setHost("rabbitmq.study.com");
        factory.setUsername("admin");
        factory.setPassword("123456");

        String queueName = "queue1";


        // 3、从连接工厂获取连接
        Connection connection = factory.newConnection("消费者");

        // 4、从链接中创建通道
        Channel channel = connection.createChannel();

        /**
         * 5、声明（创建）队列 如果队列不存在，才会创建 RabbitMQ 不允许声明两个队列名相同，属性不同的队列，否则会报错
         *
         * queueDeclare参数说明：
         *
         * @param queue
         *            队列名称
         * @param durable
         *            队列是否持久化
         * @param exclusive
         *            是否排他，即是否为私有的，如果为true,会对当前队列加锁，其它通道不能访问，
         *            并且在连接关闭时会自动删除，不受持久化和自动删除的属性控制。 一般在队列和交换器绑定时使用
         * @param autoDelete
         *            是否自动删除，当最后一个消费者断开连接之后是否自动删除
         * @param arguments
         *            队列参数，设置队列的有效期、消息最大长度、队列中所有消息的生命周期等等
         */
        channel.queueDeclare(queueName, false, false, false, null);

        // 6、定义收到消息后的回调
        DeliverCallback callback = new DeliverCallback() {
            public void handle(String consumerTag, Delivery message) throws IOException {
                // 收到消息，模拟进行业务处理
                System.out.println("consumerTag=" + consumerTag);
                System.out.println("收到消息：" + new String(message.getBody(), "UTF-8"));

                // 手动签收消息
                //channel.basicAck(message.getEnvelope().getDeliveryTag(), Boolean.FALSE);
            }
        };

        // 7、开启队列消费
        channel.basicConsume(queueName, false, callback, new CancelCallback() {
            public void handle(String consumerTag) throws IOException {
            }
        });

        System.out.println("开始接收消息");
        System.in.read();

    }
}
