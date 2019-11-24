package com.liuli.tk.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConditionalOnClass(RestTemplate.class)
public class RestAutoConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("****restTemplate created!****");
		return restTemplate;
	}
}
