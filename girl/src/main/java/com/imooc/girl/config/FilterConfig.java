package com.imooc.girl.config;

import com.imooc.girl.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean authRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(1); //设置执行顺序 ，数字越小越先执行
        filterRegistrationBean.setFilter(authFilter());   // filterRegistrationBean.setFilter(new AuthFilter()());   ,直接new，不会注入依赖
        filterRegistrationBean.setName("authFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    /**
     * 声明成spring管理的bean，这样才会注入依赖
     *
     * @return
     */
    @Bean
    public Filter authFilter() {
        return new AuthFilter();
    }


}
