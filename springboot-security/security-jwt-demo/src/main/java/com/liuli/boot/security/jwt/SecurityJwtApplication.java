package com.liuli.boot.security.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.liuli.boot"})
public class SecurityJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApplication.class, args);
    }
}
