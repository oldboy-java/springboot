package com.tk.rabbitmq.boot.s01_helloworld;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;


@Component
public class HelloWorldConsumer {


	@RabbitListener(queues = "hello")
	@RabbitHandler
	public void receive(@Payload User user, @Headers Map<String, Object> headers, Channel channel) throws Exception{
		System.out.println(headers.get(AmqpHeaders.CONSUMER_TAG) + " [x] Received '" + user.getName()+ "'");
		
//		TimeUnit.MINUTES.sleep(1);
		
		channel.basicAck((long)headers.get(AmqpHeaders.DELIVERY_TAG), false);
		
		
	}
}
