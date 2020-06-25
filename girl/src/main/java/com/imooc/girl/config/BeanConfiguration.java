package com.imooc.girl.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;

import com.imooc.girl.bean.Red;
import com.imooc.girl.bean.Yellow;

/**
 * 主要演示Bean的注册
 * @author Administrator
 */

@Configuration
//使用@Import快速引入组件，组件的ID默然是全类名
@Import({Red.class, Yellow.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class}) 
public class BeanConfiguration implements ApplicationContextAware{ //实现ApplicationContextAware可以获取Spring上下文

	private ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
		System.err.println(this.ctx);
	}

	
	
}
