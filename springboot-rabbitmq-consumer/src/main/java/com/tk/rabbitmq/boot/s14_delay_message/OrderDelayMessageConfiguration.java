package com.tk.rabbitmq.boot.s14_delay_message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *   死信队列案例相关配置
 */
@Configuration
public class OrderDelayMessageConfiguration {

    /**
     *  定义正常队列，设置死信交换机、死信路由Key
     * @return
     */
    @Bean
    public Queue  orderQueue(TopicExchange orderExchangeDlx) {
        Queue queue =  new Queue("order-queue");

//        // 设置队列绑定的死信交换机
        queue.getArguments().put("x-dead-letter-exchange", orderExchangeDlx.getName());
//
//       // 设置队列的死信路由key
        queue.getArguments().put("x-dead-letter-routing-key", "order.*");

        // 给队列设置过期时间
        queue.getArguments().put("x-message-ttl", 15000);

        return queue;
    }


    /**
     *  定义正常交换机
     * @return
     */
    @Bean
    public TopicExchange  orderExchange(){
        TopicExchange exchange = new TopicExchange("order-exchange");
        return exchange;
    }


    @Bean
    public Binding  testBindingDlx(TopicExchange orderExchange, Queue orderQueue) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order.*");
    }




    /**
     *  死信队列队列
     * @return
     */
    @Bean
    public Queue  orderQueueDlx() {
        Queue queue =  new Queue("order-queue-dlx");
        return queue;
    }


    /**
     *  死信交换机
     * @return
     */
    @Bean
    public TopicExchange  orderExchangeDlx(){
        TopicExchange exchange = new TopicExchange("order-exchange-dlx");
        return exchange;
    }

    @Bean
    public Binding bindingDlx(TopicExchange orderExchangeDlx, Queue orderQueueDlx) {
        return BindingBuilder.bind(orderQueueDlx).to(orderExchangeDlx).with("order.*");
    }



}
