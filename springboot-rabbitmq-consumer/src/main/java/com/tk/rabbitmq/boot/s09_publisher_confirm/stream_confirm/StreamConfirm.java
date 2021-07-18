package com.tk.rabbitmq.boot.s09_publisher_confirm.stream_confirm;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@EnableScheduling
public class StreamConfirm {

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange("spring-Routing-return");
	}

	@Bean
	public Queue  queue(){
		return new Queue("spring-Routing-return-queue");
	}

	@Bean
	public Binding binding(TopicExchange exchange, Queue queue){
		return   BindingBuilder.bind(queue).to(exchange).with("*.return");
	}



	@Bean
	public RabbitTemplate  rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		// 设置发布确认回调,一个RabbitTemplate只可设置一个回调。
		template.setConfirmCallback(confirmCallback());
		
		// 设置消息没有路由到队列时强制回退，调用回调方法，默认是直接丢弃。
		template.setMandatory(true);

		//  设置消息退回回调 【注意】一个 RabbitTemplate 只能设置一个 ReturnCallback
		template.setReturnCallback(returnCallback());

		return template;
	}

	/**
	 *  设置消息回退回调
	 * @return
	 */
	private RabbitTemplate.ReturnCallback returnCallback(){
		return new RabbitTemplate.ReturnCallback() {

			/**
			 *
			 * @param message  消息对象
			 * @param replyCode 错误码
			 * @param replyText 错误消息
			 * @param exchange 交换机
			 * @param routingKey 路由键
			 */
			@Override
			public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
				// 在这里写退回处理逻辑
				System.out.println("收到回退消息 replyCode=" + replyCode + " replyText=" + replyText + " exchange=" + exchange
						+ " routingKey=" + routingKey);

				System.out.println(" 消息：" + message);
			}
		};
	}

	// 创建ConfirmCallback实例的方法
	private ConfirmCallback confirmCallback() {
		return new ConfirmCallback() {

			@Override
			// correlationData 发布消息时指定的关联数据（消息的唯一ID）
			// ack  交换机是否成功收到消息 true 成功收到  false 代表失败
			// cause  失败的原因
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				if (ack) { // 确认成功
					System.out.println(" 发布确认成功");
				} else { // 确认失败
					System.out.println(" 发布确认失败");
					// 重新发送
				}
//				System.out.println(" cause=" + cause + " correlationData=" + correlationData);
				
				// 根据关联数据的id从待确认消息Map中移除消息
//				System.out.println("消息为：" + messageMap.remove(correlationData.getId()));
			}
		};
	}

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private TopicExchange exchange;

	// 消息计数
	long count = 0;

	// 存放待确认消息的map
	private final Map<String, String> messageMap = new ConcurrentHashMap<>();

	@Scheduled(fixedDelay = 1000) // 定时多次发送消息
	public void send() {
		String message = "Hello World!" + (++count);
		String id = "id-" + count;
		// 将消息以一个唯一id为key放入待确认Map
		messageMap.put(id, message);

		// 以唯一标识id 作为id创建关联数据对象
		CorrelationData correlationData = new CorrelationData(id);

		this.template.convertAndSend(exchange.getName(), "routingkeyaaa", message, correlationData);
		System.out.println(" [x] Sent '" + message + "'");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(StreamConfirm.class, args);
		// 按任意键退出程序
		System.in.read();
	}
}
