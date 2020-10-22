package com.imooc.girl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.imooc.girl.config.Bean1AutoConfiguration;
import com.imooc.girl.utils.SpringContextUtils;

@SpringBootApplication
public class GirlApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(GirlApplication.class, args);
		
//		Bean1AutoConfiguration bean1 = SpringContextUtils.getBean(Bean1AutoConfiguration.class);
//		System.err.println(bean1);
	}
}
