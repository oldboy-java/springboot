package com.imooc.girl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringValueResolver;

import com.imooc.girl.pojo.Db;

import lombok.extern.slf4j.Slf4j;


@Configuration
@PropertySource("classpath:/db.properties")
@Slf4j
/// @Profile:指定组件在哪个环境的情况下才能被注册到容器中，不指定，任何环境下都能注册这个组件。
// 1) 加了环境标识的Bean，只有这个环境被激活的时候才会被注册到容器中，默认是default环境
// 2) @Profile写在类上，只有指定对应环境的时候，整个配置类里面的所有配置才会生效
// 3) 没有标注任何环境的Bean，在任何环境下都能被注册到容器中
public class DbConfig implements EmbeddedValueResolverAware {

    private StringValueResolver stringValueResolver;

    @Autowired
    private Environment env;

    @Value("${db.user}")
    private String user;

    @Value("${db.pwd}")
    private String pwd;

    @Bean
    public Db db() {
        // 配置文件中不存在属性，则会抛异常
        String user = env.getRequiredProperty("db.user");
        log.info("db.user={}", user);
        // 配置文件中不存在属性，返回null
        String pwd = env.getProperty("db.pwd");
        log.info("db.pwd={}", pwd);
        return new Db(user, pwd);
    }

    @Bean("db2")
    @Profile("dev")  //只在dev环境下创建db2实例
    public Db db2() {
        log.info("db2.user={}", user);
        log.info("db2.pwd={}", pwd);
        return new Db(user, pwd);
    }

    @Bean("db3")
    @Profile("prod") //只在prod环境下创建db3实例
    public Db db3() {
        String user = stringValueResolver.resolveStringValue("${db.user}");
        String pwd = stringValueResolver.resolveStringValue("${db.pwd}");
        log.info("db3.user={}", user);
        log.info("db3.pwd={}", pwd);
        return new Db(user, pwd);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolver = resolver;
    }
}
