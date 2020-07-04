package com.tk.rabbitmq.boot.s02_workQueue;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.rabbitmq.client.Channel;

@SpringBootApplication
@EnableScheduling
public class WorkQueueConsumer {

	/**
	 * 
	 * concurrency配置初始并发和最大并发数，rabbitmq会根据配置和消息消费情况，创建新的消费者直到到达最大并发数
	 */
	@RabbitListener(queues = "hello", concurrency = "2-5", containerFactory = "myFactory") // 并发2到5个消费者
	public void receive(@Payload String message, @Headers Map<String, Object> headers, Channel channel) throws Exception {
		System.out.println(headers.get(AmqpHeaders.CONSUMER_TAG) + "--Channel-" + channel.getChannelNumber() + " Received '" + message + "'");
		TimeUnit.SECONDS.sleep(10);
	}

	@Bean
	public Queue hello() {
		return new Queue("hello");
	}

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private Queue queue;

	AtomicInteger count = new AtomicInteger();

	@Scheduled(fixedDelay = 1000) // 定时多次发送消息
	public void send() {
		String message = "Hello World!" + count.incrementAndGet();
		this.template.convertAndSend(queue.getName(), message);
		System.out.println(" [x] Sent '" + message + "'");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WorkQueueConsumer.class, args);
		System.in.read();
	}
}
