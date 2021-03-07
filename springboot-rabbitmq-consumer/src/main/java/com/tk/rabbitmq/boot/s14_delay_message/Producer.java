package com.tk.rabbitmq.boot.s14_delay_message;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling // spring 中的定时功能，此处只是为了多次发送消息
public class Producer {

	@Bean
	public FanoutExchange fanout() {
		FanoutExchange ex = new FanoutExchange("my-exchange");
		ex.setDelayed(true);
		return ex;
	}

	@Bean
	public Queue lazyMessageQueue() {
		return new AnonymousQueue();
	}
	
	
	@Bean
	public Binding binding1a(FanoutExchange fanout, Queue lazyMessageQueue) {
		// 绑定队列与交换机，同时设置绑定key
		return BindingBuilder.bind(lazyMessageQueue).to(fanout);
	}
	
	
	@Autowired
	private RabbitTemplate template;

	@Autowired
	private FanoutExchange fanout;

	@Scheduled(fixedDelay = 1000) // 定时多次发送消息
	public void send() {
		String message = "Hello World!-" + System.currentTimeMillis() / 1000;


		// 消息后置处理器
		MessagePostProcessor processor = new MessagePostProcessor() {

			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				// 设置消息持久化
				message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
				// 设置消息延时时间：延时5秒
				message.getMessageProperties().setDelay(5000);

			//	message.getMessageProperties().setHeader("x-delay", 5000);
				return message;
			}

		};

		template.convertAndSend(fanout.getName(), "", message, processor);
		System.out.println(" [x] Sent '" + message + "'");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Producer.class, args);
		System.in.read();
	}
}
