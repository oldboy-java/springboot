package com.tk.rabbitmq.boot.s015_dlx;



import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import  org.junit.jupiter.api.Test;


import java.util.stream.IntStream;


@SpringBootTest
public class DLXTest {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange testExchangeDlx;


    /**
     * 验证队列设置过期时间，消息达到过期时间后进入死信队列
     */
    @Test
    public void testSendMsg() {
        String message = "张三【1000】的订单已提交";
        this.template.convertAndSend("test_exchange_dlx", "test.order.618", message);
    }


    /**
     * 验证消息数理超出队列长度限制，超出队列长度后，会从队列顶部将多余消息转移到死信队列中
     */
    @Test
    public void testSendMsg2() {

        IntStream.rangeClosed(1, 15).forEach(r -> {
            String message = "张三" + r + "【" + 1000 + r + "】的订单已提交";
            this.template.convertAndSend("test_exchange_dlx", "test.order.618", message);
        });

    }


    /**
     * 验证消费者拒绝签收消息，消息进入死信队列
     */
    @Test
    public void testSendMsg3() {
        String message = "我是一条正常消息，消息费拒绝签收我。。。只能进入死信队列";
        this.template.convertAndSend("test_exchange_dlx", "test.order.618", message);
    }
}
