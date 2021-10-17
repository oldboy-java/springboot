package com.glodon.limiter.annatation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimiter {


    /**
     *  每单位时间内限流阀值
     * @return
     */
    int limit() default  1;

    /**
     *  限流阀值单位，默认每秒
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 方法Key
     * @return
     */
    String methodKey() default "";

}
