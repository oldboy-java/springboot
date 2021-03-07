package com.tk.rabbitmq.boot.s08_expiration_message;


import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

//@SpringBootTest注解会自动检索程序的配置文件，
// 检索顺序是从当前包开始，逐级向上查找被@SpringBootApplication或@SpringBootConfiguration注解的类
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExpirationMessageTest {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;


	/**
	 *  发送消息，使用队列中设置的过期时间
	 */
	@Test
	public  void  testSendMsgWithQueueExpiration() {
		String message = "字符串消息：我是中国人";
		this.template.convertAndSend(queue.getName(), message);
		System.out.println(" [x] Sent '" + message + "'");
	}


	/**
	 *  消息本身也设置过期时间15000毫秒，时间比队列中的短，所以在15秒后过期
	 */
	@Test
	public   void testSendMsgWithExpiration() {
		String message = "Hello World!";

		// messagePostProcessor  消息处理器，在消息发送前可以对消息进行处理
		this.template.convertAndSend(queue.getName(), message, msg -> {
			msg.getMessageProperties().setExpiration("15000"); // 设置消息的过期时间;
			return msg;
		});
		System.out.println(" [x] Sent '" + message + "'");
	}

	/**
	 * 同时发送消息，将中间的消息过期时间设置比队列中的短。但队列前面的消息未被消费，
	 * 此时中间本应该过期的消息也不会过期。因为消息只有在队列顶部被消费时才判断是否过期
	 */
	@Test
	public   void testSend() {

		String message = "Hello World!";
		IntStream.range(1, 5) .forEach(i ->{

			Person p = new Person();
			p.setId(i);
			p.setName(message + "-" +i);
			System.out.println(" [x] Sent '" + JSON.toJSONString(p) );

			if (i  ==3) {
				// messagePostProcessor  消息处理器，在消息发送前可以对消息进行处理
				this.template.convertAndSend(queue.getName(), p, msg -> {
					msg.getMessageProperties().setExpiration("15000"); // 设置消息的过期时间;
					return msg;
				});
			}else {
				this.template.convertAndSend(queue.getName(), p);
			}
		});
	}


	/***
	 *  发送自定义对象消息
	 */
	@Test
	public   void testSendPersonMsg() {
			Person person =  new Person();
			person.setId(122233);
			person.setName("刘董事长");
			this.template.convertAndSend(queue.getName(), person);
	}

	/**
	 *  发送消息，使用队列中设置的过期时间
	 */
	@Test
	public  void  testSendMapMsg() {
		Map<String,Object> data = new HashMap<>();
		data.put("id",1);
		data.put("name","张大大");
		this.template.convertAndSend(queue.getName(),data);
	}

	/**
	 *  发送消息，使用队列中设置的过期时间
	 */
	@Test
	public  void  testSendByteArrayMsg() {
		String message = "Byte数组消息：我是中国人";
		this.template.convertAndSend(queue.getName(), message.getBytes());
	}
}
