package com.tk.rabbitmq.boot.s14_delay_message;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

//	@RabbitListener(queues = ""delay-queue")
@RabbitListener(queues = "order-queue-dlx")
@RabbitHandler
	public void receive1( Channel channel, String in, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
		log.error("Channel-{} Received msg ={} in {}" ,  channel.getChannelNumber()  , in,  System.currentTimeMillis());
		try {

			// 这里可以假设发送消息发送的是延迟30分钟后订单失效消息
			// 这里收到消息后，可以调用接口获取订单状态，如果当前订单状态未支付，则关闭30分钟内未支付的订单

			// 手动签收
			channel.basicAck(deliveryTag, false);
		} catch (Exception e) {
			log.error("{}",e);
		}
	}

}
