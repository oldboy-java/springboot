package com.liuli.boot.security.jwt.secuity.handler;

import com.alibaba.fastjson.JSON;
import com.liuli.boot.security.jwt.dto.ResponseResult;
import com.liuli.boot.security.jwt.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义授权异常处理器
 * 使用统一json格式返回异常信息
 *   {
 *       "code":500,
 *       "data":null,
 *       "msg:"xxxx"
 *   }
 */
@Component
public class AuthorizationFailHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult responseResult = ResponseResult.error(HttpStatus.FORBIDDEN.value(), "用户权限不足");
        WebUtils.renderJson(response, JSON.toJSONString(responseResult));
    }
}
