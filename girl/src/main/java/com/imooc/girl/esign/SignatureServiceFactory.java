package com.imooc.girl.esign;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SignatureServiceFactory implements ApplicationContextAware {

    private  static  ApplicationContext context;

    public static  SignatureService  getSignatureService(String className) throws ClassNotFoundException {
      Class cls = Class.forName(className);
      return (SignatureService) context.getBean(cls);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
