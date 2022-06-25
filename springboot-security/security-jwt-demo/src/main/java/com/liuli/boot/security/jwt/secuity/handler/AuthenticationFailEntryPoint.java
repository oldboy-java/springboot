package com.liuli.boot.security.jwt.secuity.handler;

import com.alibaba.fastjson.JSON;
import com.liuli.boot.security.jwt.dto.ResponseResult;
import com.liuli.boot.security.jwt.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证异常处理器
 * 使用统一json格式返回异常信息
 *   {
 *       "code":500,
 *       "data":null,
 *       "msg:"xxxx"
 *   }
 */
@Component
public class AuthenticationFailEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult responseResult = ResponseResult.error(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
        WebUtils.renderJson(response, JSON.toJSONString(responseResult));
    }
}
