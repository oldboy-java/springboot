package com.tk.rabbitmq.boot.s14_delay_message;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class Producer {


    @Autowired
    private RabbitTemplate template;


    @Test
    public void testSend() {
        String message = "Hello World!-" + System.currentTimeMillis() / 1000;
        // 消息后置处理器
        MessagePostProcessor processor = new MessagePostProcessor() {

            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 设置消息持久化
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);

                // 设置消息延时时间：延时10秒
                message.getMessageProperties().setDelay(10000);

                //	message.getMessageProperties().setHeader("x-delay", 5000);
                return message;
            }
        };

        template.convertAndSend("delay-exchange", "", message, processor);

        for (int i = 10; i > 0; i--) {
            log.info("倒计时:{}", i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void testSend2() throws IOException {
        String message = "张三的订单";

        // 使用TTL + 死信队列
        template.convertAndSend("order-exchange", "order.1001", message);


        for (int i = 10; i > 0; i--) {
            log.info("倒计时:{}", i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //  此行代码是为了执行该测试方法后，程序不要退出，等监听器监听消息可以执行
        System.in.read();

    }
}
