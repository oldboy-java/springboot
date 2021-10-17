package com.glodon.limiter.aspect;

import com.glodon.limiter.annatation.AccessLimiter;
import com.glodon.limiter.exception.RateLimitException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class AccessLimiterAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisScript<Boolean>  redisScriptLua;

    @Pointcut(value = "@annotation(com.glodon.limiter.annatation.AccessLimiter)")
    public  void pointcut(){
        log.info("pointcut....");
    }

    @Before("pointcut()")
    public void  accessLimitBefore(JoinPoint joinPoint){
        // 获取方法签名作为methodKey
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        AccessLimiter annotation = method.getAnnotation(AccessLimiter.class);
        if (annotation == null){
            return;
        }

        int limit = annotation.limit();
        String methodKey = annotation.methodKey();
        TimeUnit timeUnit = annotation.timeUnit();

        //如果没有指定methodKey，则默认生成key: 方法名#参数类型名1,参数类型名2
        if (StringUtils.isBlank(methodKey)) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            String paramTypeName = Arrays.stream(parameterTypes).map(Class::getName).collect(Collectors.joining(","));
            methodKey = method.getName() +"#" + paramTypeName;
        }

        //调用redis,由于使用的是StringRedisTemplate,序列化时必须保证序列化参数是字符串类型，必须保证args参数时字符串类型
        Boolean acquire = stringRedisTemplate.execute(redisScriptLua,
                Lists.newArrayList(methodKey),
                String.valueOf(limit),   // 限流大小转换成字符串类型
                timeUnit.name());       //限流单位转成字符串
        if (Boolean.FALSE.equals(acquire)){
            log.error("The current request is limited the rate of  {}/ {}, Your  request access is blocked, methodKey={}",
                    limit, timeUnit.name(), methodKey);
            throw new RateLimitException(500,
                    "The current request is limited the rate of " + limit + "/" + timeUnit.name() +
                            " , cause your  request access is blocked, methodKey is " + methodKey );
        }
    }
}
