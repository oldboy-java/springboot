package com.tk.rabbitmq.boot.s14_delay_message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

//@Configuration
public class DelayMessageConfiguration {

    @Bean
    public FanoutExchange fanout() {
        FanoutExchange ex = new FanoutExchange("delay-exchange");
        ex.setDelayed(true);
        return ex;
    }

    @Bean
    public Queue queue() {
        Queue queue = new Queue("delay-queue");
        return queue;
    }


    @Bean
    public Binding binding1a(FanoutExchange fanout, Queue queue) {
        // 绑定队列与交换机，同时设置绑定key
        return BindingBuilder.bind(queue).to(fanout);
    }

}
