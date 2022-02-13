package com.liuli.helloworld;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@SpringBootApplication
public class HelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
    }


    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		/*return  new CommandLineRunner(){
			@Override
			public void run(String... args){
				System.out.println("来看看 SpringBoot 默认为我们提供的 Bean：");
				String[] beanNames = ctx.getBeanDefinitionNames();
				Arrays.sort(beanNames);
				Arrays.stream(beanNames).forEach(System.out::println);
			}
		};*/

        return args -> {
            System.out.println("来看看 SpringBoot 默认为我们提供的 Bean：");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            Arrays.stream(beanNames).forEach(System.out::println);
        };

    }
}
