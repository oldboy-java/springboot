package com.tk.rabbitmq.boot.s05_topic;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	@RabbitListener(queues = "#{autoDeleteQueue1.name}")
	public void receive1(Channel channel, String in) {
		System.out.println("Channel-" + channel.getChannelNumber() + " Received '" + in + "'");
	}

//	@RabbitListener(queues = "#{autoDeleteQueue2.name}")
//	public void receive2(Channel channel, String in) {
//		System.out.println("Channel-" + channel.getChannelNumber() + " Received '" + in + "'");
//	}
}
