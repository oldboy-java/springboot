package com.tk.rabbitmq.boot.s08_expiration_message;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class Consumer {

	@RabbitListener(queues = "spring-queue8")
	@RabbitHandler
	public void receivePersonMsg(@Payload Person person, Channel channel, @Headers Map<String ,Object> headers) {//指定头
		log.info("----------"+ JSON.toJSON(person));
		if( person.getId() < 3) {
			try {
				channel.basicAck((long)headers.get(AmqpHeaders.DELIVERY_TAG), false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


//	@RabbitListener(queues = "spring-queue8")
//	public void receiveStringMsg(@Payload String message,    //消息体
//						@Headers Map<String, Object> headers,  //消息头
//						@Header("contentType") String contentType,  Channel channe) throws IOException {//指定头
//		log.info("----------"+ JSON.toJSON(message));
//
//		// 手动确认消息
////		channe.basicAck((long)headers.get(AmqpHeaders.DELIVERY_TAG), false);
//	}

//	@RabbitListener(queues = "spring-queue8")
//	public void receiveByteMsg(@Payload byte[] message,    //消息体
//							   @Headers Map<String, Object> headers,  //消息头
//							   @Header("contentType") String contentType) {//指定头
//		log.info("----------"+ JSON.toJSON(new String(message)));
//	}
//
//
//	@RabbitListener(queues = "spring-queue8")
//	public void receiveMapMsg(@Payload Map message,    //消息体
//							  @Headers Map<String, Object> headers,  //消息头
//							  @Header("contentType") String contentType) {//指定头
//		log.info("----------"+ JSON.toJSON(message));
//	}

}
