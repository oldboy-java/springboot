package com.liuli.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置在application.properties文件中的信息，默认可以直接读取到，不需要
 * 通过@PropertySource("classpath:config/my2.properties") //指定配置文件路径
 */

@Component
@ConfigurationProperties(prefix = "my1")
public class MyProperties1 {
    private Integer age;
    private String name;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyProperties1{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
