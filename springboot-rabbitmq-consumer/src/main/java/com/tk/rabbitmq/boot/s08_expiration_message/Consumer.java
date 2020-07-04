package com.tk.rabbitmq.boot.s08_expiration_message;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	@RabbitListener(queues = "spring-queue8")
	public void receive(@Payload String in,    //消息体
						@Headers Map<String, Object> headers,  //消息头
						@Header("amqp_expiration") String expiration, //指定头
						@Header("amqp_replyTo") String replyTo, //指定头
						@Header("contentType") String contentType) {//指定头
		System.out.println(" [x] Received '" + in + "'");
		System.out.println(headers);
		System.out.println(expiration);
		System.out.println(replyTo);
		System.out.println(contentType);
	}

}
