package com.imooc.girl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.imooc.girl.mapper.GirlMapper;
import com.imooc.girl.pojo.Girl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * GirlService2类中使用了缓存注解，需要生成动态代理，虽然配置类中指明：spring.aop.proxy-target-class=false使用JDK动态代理
 * 但由于没有接口，还是使用CGLIB生成动态代理
 */
@Service
public class GirlService2 {

    @Autowired
    private GirlMapper mapper;


    @Cacheable(value = "girl2", key = "#root.method.name", sync = true) //指定多个线程同时执行时使用本地同步锁，一定程度上解决缓存击穿问题
    public List<Girl> girlList() {
        EntityWrapper wrapper = new EntityWrapper<>();
        return mapper.selectList(wrapper);
    }


}
