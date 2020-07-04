package com.tk.rabbitmq.boot.s01_helloworld;

import org.json.JSONObject;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling  //spring 中的定时功能，此处只是为了多次发送消息
public class HelloWorldProducer {
	
	//spring boot 中 amqp的使用说明： 
	//https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-messaging.html#boot-features-amqp

	/*
	 * 【注意】这里配置一个我们要操作的Queue的bean,spring-rabbitmq框架在启动时将从容器中获取这些bean,
	 * 并向rabbitmq服务器创建这些queue、exchange、binding。
	 * 在RabbitAdmin.initialize()方法中做的这个事。
	 * 其完成的工作为：channel.queueDeclare("hello",false, false, false, null);
	 * 我们也可以自己手动通过 AmqpAdmin.declareXXX(xxx)方法来创建我们需要的queue、exchange、binding。
	 * 
	 * @Autowired private AmqpAdmin amqpAdmin;
	 * 
	 * public void send() { 
	 * 		... 
	 * 		this.amqpAdmin.declareQueue(new Queue("hello"));
	 * 		... 
	 * }
	 */
	@Bean
	public Queue hello() {
		return new Queue("hello");
	}
	
	/**
	 * 自定义json消息转换器,   默认是简单消息转换器,对象必须实现Serializable接口，否则会失败。采用json消息转换器，对象可以不实现Serializable接口
	 * @return
	 */
	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	// @Autowired  
	// private AmqpAdmin amqpAdmin;   //做queue、exchange、binding的管理用的

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private Queue queue;

	@Scheduled(fixedDelay = 1000)   //定时多次发送消息
	public void send() {
//		String message = "Hello World!";
		User user = new User("Mr Liu", 35);
		// 使用默认交换机，点击发送消息，路由key等于队列名称
		this.template.convertAndSend(queue.getName(), user);
		System.out.println(" [x] Sent '" + user + "'");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HelloWorldProducer.class, args);
		System.in.read();
	}
}
