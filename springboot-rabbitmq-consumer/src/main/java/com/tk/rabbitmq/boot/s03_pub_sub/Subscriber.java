package com.tk.rabbitmq.boot.s03_pub_sub;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class Subscriber {

    // 配置多个消费者：
    //1、可以是指定多个标注@RabbitListener的方法，定义监听不同队列的消费者
    //2、配置并发，可以在配置文件中定义，也可以通过@RabbitListener中的concurrency来指定，格式为：最小并发-最大并发

    // 定义消费者1
    @RabbitListener(queues = "#{autoDeleteQueue1.name}")  // 通过Spring容器中的bean来获取队列名称
    public void receive1(Channel channel, @Payload String in) {
        System.out.println("Channel-" + channel.getChannelNumber() + " Received '" + in + "'");
    }

    // 定义消费者2
    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(Channel channel, @Payload String in) {
        System.out.println("Channel-" + channel.getChannelNumber() + " Received '" + in + "'");
    }
}
