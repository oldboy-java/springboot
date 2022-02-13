package com.imooc.girl.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.imooc.girl.bean.Pink;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    // AnnotationMetadata: 当前标注@Import注解的类所有注解信息
    // BeanDefinitionRegistry：Bean定义注册表，通过它手工注册Bean到容器中
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //Red和Yellow是通过@import来注册到容器中的，bean的ID是全类名
        boolean definition = registry.containsBeanDefinition("com.imooc.girl.bean.Red");
        boolean definition2 = registry.containsBeanDefinition("com.imooc.girl.bean.Yellow");

        // 容器中同时存在Red和Yellow时，手动注册Pink
        if (definition && definition2) {
            //Bean定义
            BeanDefinition beanDefinition = new RootBeanDefinition(Pink.class);

            // pink指定Bean的id
            registry.registerBeanDefinition("pink", beanDefinition);
        }

    }

}
