package com.imooc.girl.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.imooc.girl.service.GirlService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private GirlService girlService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("在拦截器中注入GirlService bean is :{}", (girlService == null ? "failed" : "success"));

        return super.preHandle(request, response, handler);
    }

}
