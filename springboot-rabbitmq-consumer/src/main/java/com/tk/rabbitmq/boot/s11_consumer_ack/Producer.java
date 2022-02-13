package com.tk.rabbitmq.boot.s11_consumer_ack;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class Producer {

    @Bean
    public Queue ack() {
        return new Queue("ack");
    }

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    int count = 0;

    @Scheduled(fixedDelay = 1000)
    public void send() {
        String message = "Hello World!-" + ++count;
        this.template.convertAndSend(queue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Producer.class, args);
        System.in.read();
    }
}
