package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//扫描mybatis mapper包路径
@MapperScan(basePackages= {"com.example.demo.mapper"})

//定义扫描所有需要的包，包括其他非启动类所在包，默认springboot只扫描启动类所在的包及子包下的注解
@ComponentScan(basePackages= {"com.example.demo","org.n3r.idworker"})

//开启定时任务
//@EnableScheduling

//开启异步任务
@EnableAsync

//@EnableHttpClient

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
