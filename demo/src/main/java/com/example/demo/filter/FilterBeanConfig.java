package com.example.demo.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterBeanConfig {

	
	/***
	 *  1、构造filter
	 *  2、配置拦截的urlPattern
	 *  3、利用FilterRegistrationBean进行包装
	 * @return
	 */
	@Bean
	public FilterRegistrationBean logFilter() {
		FilterRegistrationBean filterRegistrationBean  = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new LogFilter());
		
		List<String> urlList = new ArrayList<String>();
		urlList.add("*");
		filterRegistrationBean.setUrlPatterns(urlList);
		return filterRegistrationBean;
	}
}
