package com.tk.rabbitmq.boot.s05_topic;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfiguration {

	@Bean
	public TopicExchange topic() {
		return new TopicExchange("spring.topic");
	}

	@Configuration
	public static class ReceiverConfig {

		@Bean
		public Queue autoDeleteQueue1() {
			return new AnonymousQueue();
		}

		@Bean
		public Queue autoDeleteQueue2() {
			return new AnonymousQueue();
		}

		@Bean
		public Binding binding1a(TopicExchange topic, Queue autoDeleteQueue1) {
			// 这里routingKey可以是固定的某个值：如orange，此时等同于直连交换机
			return BindingBuilder.bind(autoDeleteQueue1).to(topic).with("*.orange.*");
		}

		@Bean
		public Binding binding2a(TopicExchange topic, Queue autoDeleteQueue2) {
			return BindingBuilder.bind(autoDeleteQueue2).to(topic).with("*.*.rabbit");
		}

		@Bean
		public Binding binding2b(TopicExchange topic, Queue autoDeleteQueue2) {
			return BindingBuilder.bind(autoDeleteQueue2).to(topic).with("lazy.#");
		}
	}
}
