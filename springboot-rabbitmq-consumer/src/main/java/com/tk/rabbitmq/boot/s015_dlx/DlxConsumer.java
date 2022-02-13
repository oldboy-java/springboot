package com.tk.rabbitmq.boot.s015_dlx;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class DlxConsumer {

    @RabbitListener(queues = "test_queue-dlx")
    @RabbitHandler
    public void receiveMsg(String message, Channel channel, @Headers Map<String, Object> headers) {
        // 执行业务逻辑处理 .......
        try {

            // 模拟出现异常
            int i = 3 / 0;
            // 处理完签收消息
            channel.basicAck((long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
        } catch (Exception e) {
            log.error("{}", e);
            // 出现异常拒绝签收
            try {
                channel.basicNack((long) headers.get(AmqpHeaders.DELIVERY_TAG), false, false);
            } catch (IOException ioException) {
                log.error("{}", ioException);
            }
        }
    }

}
