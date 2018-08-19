package com.example.demo.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration  //声明是配置类
@ConfigurationProperties(prefix="com.imooc.opensource")  //解析prefix="com.imooc.opensource"的配置
@PropertySource(encoding="UTF-8",value= {"classpath:resource.properties"}) //定义配置文件的路径
public class Resource {

	private String name;
	private String website;
	private String language;
}
