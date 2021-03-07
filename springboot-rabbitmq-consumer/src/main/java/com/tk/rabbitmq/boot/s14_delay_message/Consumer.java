package com.tk.rabbitmq.boot.s14_delay_message;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer {

	@RabbitListener(queues = "#{lazyMessageQueue.name}")  //使用了匿名队列
	public void receive1(Channel channel, String in, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
		System.out.println("Channel-" + channel.getChannelNumber() + " Received '" + in + "-'" + System.currentTimeMillis()/ 1000);
		try {

			// 这里可以假设发送消息发送的是延迟30分钟后订单失效消息
			// 这里收到消息后，可以调用接口获取订单状态，如果当前订单状态未支付，则关闭30分钟内未支付的订单

			// 手动签收
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
