package com.imooc.girl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.imooc.girl.converter.StringToGirlConverter;

@Configuration
public class SpringMvcWebConfig extends WebMvcConfigurationSupport {
	
	@Override
	protected void addFormatters(FormatterRegistry registry) {
		Converter converter = new StringToGirlConverter();
		 //添加自定义转换器
        registry.addConverter(converter);
	}
}
