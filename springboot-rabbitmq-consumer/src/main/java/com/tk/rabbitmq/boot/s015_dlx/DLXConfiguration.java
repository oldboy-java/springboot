package com.tk.rabbitmq.boot.s015_dlx;

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
public class DLXConfiguration {

    /**
     *  定义正常队列，设置死信交换机、死信路由Key
     * @return
     */
    @Bean
    public Queue  testQueueDlx(TopicExchange exchangeDlx) {
        Queue queue =  new Queue("test_queue-dlx");

//        // 设置队列绑定的死信交换机
        queue.getArguments().put("x-dead-letter-exchange", exchangeDlx.getName());
//
//       // 设置队列的死信路由key
        queue.getArguments().put("x-dead-letter-routing-key", "order.*");

        // 给队列设置过期时间
        queue.getArguments().put("x-message-ttl", 15000);

        // 设置队列长度为10
        queue.getArguments().put("x-max-length", 10);
        return queue;
    }


    /**
     *  定义正常交换机
     * @return
     */
    @Bean
    public TopicExchange testExchangeDlx(){
        TopicExchange exchange = new TopicExchange("test_exchange_dlx");
        return exchange;
    }


    @Bean
    public Binding  testBindingDlx(TopicExchange testExchangeDlx, Queue testQueueDlx) {
        return BindingBuilder.bind(testQueueDlx).to(testExchangeDlx).with("test.order.*");
    }




    /**
     *  死信队列队列
     * @return
     */
    @Bean
    public Queue  queueDlx() {
        Queue queue =  new Queue("queue-dlx");
        return queue;
    }


    /**
     *  死信交换机
     * @return
     */
    @Bean
    public TopicExchange exchangeDlx(){
        TopicExchange exchange = new TopicExchange("exchange_dlx");
        return exchange;
    }

    @Bean
    public Binding bindingDlx(TopicExchange exchangeDlx, Queue queueDlx) {
        return BindingBuilder.bind(queueDlx).to(exchangeDlx).with("order.*");
    }



}
