package com.imooc.girl.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.imooc.girl.filter.AuthFilter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean  authFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(1); //设置执行顺序 ，数字越小越先执行
        filterRegistrationBean.setFilter(new AuthFilter());
        filterRegistrationBean.setName("authFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
	}
}
