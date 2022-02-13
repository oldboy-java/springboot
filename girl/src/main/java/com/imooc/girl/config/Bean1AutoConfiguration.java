package com.imooc.girl.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Bean1AutoConfiguration {

	public Bean1AutoConfiguration() {
		System.err.println("####Bean1AutoConfiguration 实例化#############");
	}
}
