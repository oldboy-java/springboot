package com.liuli.boot.security.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //设置允许跨域请求方式
                .allowedMethods("GET","POST","PUT","DELETE")
                //设置允许的Header属性
                .allowedHeaders("*")
                //设置允许跨域时间
                .maxAge(3600);
    }
}
