package com.tk.rabbitmq.boot.s09_publisher_confirm.routing_return;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 此例子交换机没有绑定队列，无法路由，会导致消息回退
 * @author Administrator
 *
 */
@SpringBootApplication
@EnableScheduling // spring 中的定时功能，此处只是为了多次发送消息
public class RoutingReturn {

	@Bean
	public FanoutExchange hello() {
		return new FanoutExchange("spring-Routing-return");
	}

	
	/**
	 * 默认RabbitTemplate不能设置回退回调方法，要实现消息退回回调必须自定义RabbitTemplate
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RabbitTemplate busiARabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMandatory(true); // 设置消息不可以路由退回
		// 设置消息退回回调 【注意】一个 RabbitTemplate 只能设置一个 ReturnCallback
		template.setReturnCallback(myReturnCallback());
		return template;
	}

	private ReturnCallback myReturnCallback() {
		return new ReturnCallback() {


			/***
			 *  只要消息没有投递到队列中，就会触发这个失败回调
			 * @param message 投递失败的详细信息
			 * @param replyCode  回复的状态码
			 * @param replyText   回复的文本内容
			 * @param exchange   当时这个消息发送的交换机
			 * @param routingKey 当时这个消息使用的路由键
			 */
			@Override
			public void returnedMessage(Message message, int replyCode, String replyText, String exchange,String routingKey) {

				// 在这里写退回处理逻辑
				System.out.println("收到回退消息 replyCode=" + replyCode + " replyText=" + replyText + " exchange=" + exchange
						+ " routingKey=" + routingKey);

				System.out.println(" 消息：" + message);
			}
		};
	}

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private FanoutExchange FanoutExchange;

	@Scheduled(fixedDelay = 1000) // 定时多次发送消息
	public void send() {
		// this.template.setMandatory(true);
		String message = "Hello World!";
		this.template.convertAndSend(FanoutExchange.getName(), "routingkeyaaa", message);
		System.out.println(" [x] Sent '" + message + "'");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RoutingReturn.class, args);
		// 按任意键退出程序
		System.in.read();
	}
}
