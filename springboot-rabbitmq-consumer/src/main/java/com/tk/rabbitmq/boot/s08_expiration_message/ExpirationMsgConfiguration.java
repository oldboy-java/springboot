package com.tk.rabbitmq.boot.s08_expiration_message;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpirationMsgConfiguration {

    @Bean
    public Queue hello() {
        Queue queue = new Queue("spring-queue8");
        queue.getArguments().put("x-message-ttl", 2 * 60000);
        return queue;
    }


    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

//    /**
//     *  发送消息设置消息转换器Jackson2JsonMessageConverter
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        return rabbitTemplate;
//    }


//    /***
//     *  消费消息通过设置消息监听器的使用的消息转换器Jackson2JsonMessageConverter
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        factory.setConcurrentConsumers(3);
//        factory.setMaxConcurrentConsumers(3);
//        factory.setPrefetchCount(1);
//        factory.setAutoStartup(true);
//        return factory;
//    }

}
