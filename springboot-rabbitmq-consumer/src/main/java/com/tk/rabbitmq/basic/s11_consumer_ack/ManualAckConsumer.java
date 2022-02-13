package com.tk.rabbitmq.basic.s11_consumer_ack;

import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * 简单队列消费者
 */
public class ManualAckConsumer {

    public static void main(String[] args) throws Exception {
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、设置连接属性
        factory.setHost("rabbitmq.study.com");
        factory.setUsername("admin");
        factory.setPassword("123456");

        String queueName = "queue1";

        try (
                // 3、从连接工厂获取连接
                Connection connection = factory.newConnection("消费者1");
                // 4、从链接中创建通道
                Channel channel = connection.createChannel();

                Channel channe2 = connection.createChannel();
        ) {

            // 5、声明（创建）队列 如果队列不存在，才会创建 RabbitMQ 不允许声明两个队列名相同，属性不同的队列，否则会报错
            channel.queueDeclare(queueName, false, false, false, null);

            channe2.queueDeclare(queueName, false, false, false, null);

            // 6、定义收到消息后的回调
            DeliverCallback callback = (consumerTag, message) -> {
                System.out.println(consumerTag + "-1 收到消息：" + new String(message.getBody(), "UTF-8"));

                // 进行消息处理，然后根据处理结果决定该如何确认消息。

                // 获得消息传递标识
                long deliveryTag = message.getEnvelope().getDeliveryTag();
                boolean multiple = true; // 是否批量
                boolean requeue = true; // 是否重配送
                // 手动单条确认消息ok
//				 channel.basicAck(deliveryTag, false);
                // 手动单条确认消息reject，并重配送
//				channel.basicReject(deliveryTag, requeue);
                // 手动单条确认消息reject，移除不重配送
                // channel.basicReject(deliveryTag, false);
                // 手动单条确认消息Nack，并重配送
                channel.basicNack(deliveryTag, multiple, requeue);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            DeliverCallback callback2 = (consumerTag, message) -> {
                System.out.println(consumerTag + " -2收到消息：" + new String(message.getBody(), "UTF-8"));

                // 进行消息处理，然后根据处理结果决定该如何确认消息。

                // 获得消息传递标识
                long deliveryTag = message.getEnvelope().getDeliveryTag();
                boolean multiple = true; // 是否批量
                boolean requeue = true; // 是否重配送

                // 手动单条确认消息ok
                channe2.basicAck(deliveryTag, true);

                try {
                    TimeUnit.SECONDS.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            // 设置一定的预取数量,当未确认数达到这个值时，broker将暂停配送消息给此消费者
            channel.basicQos(3);

            channe2.basicQos(3);

            // 7、注册手动确认消费者
            channel.basicConsume(queueName, false, callback, consumerTag -> {
            });


            channe2.basicConsume(queueName, false, callback2, consumerTag -> {
            });


            // String basicConsume(String queue, boolean autoAck,
            // DeliverCallback deliverCallback, CancelCallback cancelCallback)

            System.out.println("开始接收消息");
            // 按任意键退出程序
            System.in.read();

        }
    }
}
