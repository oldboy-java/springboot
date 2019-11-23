package com.liuli.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Created by Administrator on 2018-8-8.
 */
@ConfigurationProperties(prefix = "my2")//获取my2开头的配置
@Component//标志位扫描组件
@PropertySource("classpath:config/my2.properties") //指定配置文件路径
@Data
public class MyProperties2 {
    private String name;
    private String age;
    private String email;
}

