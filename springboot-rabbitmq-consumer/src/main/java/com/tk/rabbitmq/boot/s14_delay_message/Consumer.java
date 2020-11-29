package com.tk.rabbitmq.boot.s14_delay_message;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class Consumer {

	@RabbitListener(queues = "#{lazyMessageQueue.name}")  //使用了匿名队列
	public void receive1(Channel channel, String in, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
		System.out.println("Channel-" + channel.getChannelNumber() + " Received '" + in + "-'" + System.currentTimeMillis()/ 1000);
		try {
			// 手动签收
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
