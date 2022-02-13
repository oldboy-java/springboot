package com.tk.rabbitmq.boot.s08_expiration_message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpirationMessagePublisher {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExpirationMessagePublisher.class, args);

        System.in.read();
    }
}
