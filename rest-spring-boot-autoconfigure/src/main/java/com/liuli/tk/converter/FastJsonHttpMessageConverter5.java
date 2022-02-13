package com.liuli.tk.converter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Arrays;


/**
 * 默认FastJsonHttpMessageConverter的MediaType是All，Spring在解析时会以字节流进行处理，而不不会使用json
 */
public class FastJsonHttpMessageConverter5 extends FastJsonHttpMessageConverter {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public FastJsonHttpMessageConverter5() {
        setDefaultCharset(DEFAULT_CHARSET);
        setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, new MediaType("application", "*+json")));
    }
}
