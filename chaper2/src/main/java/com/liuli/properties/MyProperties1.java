package com.liuli.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 配置在application.properties文件中的信息，默认可以直接读取到，不需要
 * 通过@PropertySource("classpath:config/my2.properties") //指定配置文件路径
 * 
 * @ConfigurationProperties 注解读取加载到环境中的配置参数
 */

@Component
@ConfigurationProperties(prefix = "my1")
@Data
public class MyProperties1 {
    private String age;
    private String name;
}
