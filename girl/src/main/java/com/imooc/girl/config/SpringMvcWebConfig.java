package com.imooc.girl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.imooc.girl.converter.StringToGirlConverter;
import com.imooc.girl.interceptor.UserInterceptor;

@Configuration
public class SpringMvcWebConfig extends WebMvcConfigurationSupport {
	
	@Override
	protected void addFormatters(FormatterRegistry registry) {
		Converter converter = new StringToGirlConverter();
		 //添加自定义转换器
        registry.addConverter(converter);
	}
	
	
	/***
	 * 重新指定静态文件路径
	 */
	 @Override
	    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	        super.addResourceHandlers(registry);
	    }
	 
	 /**
	  * 配置拦截器
	  */
	 @Override
	    protected void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**");
	        super.addInterceptors(registry);
	    }
}
