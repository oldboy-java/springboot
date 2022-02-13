package com.imooc.girl.config;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.imooc.girl.bean.Red;


@RunWith(SpringRunner.class)
//@SpringBootTest
public class BeanRegistTest {

    //@Autowired
    //private ApplicationContext ctx;

    private AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfiguration.class);

    @Test
    public void testImport() {
        printBeans(ctx);

        //Red red = ctx.getBean(Red.class);

    }


    private void printBeans(ApplicationContext ctx) {
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }
}
