package com.liuli.boot.security.jwt.filter;

import com.liuli.boot.cache.RedisCache;
import com.liuli.boot.security.jwt.secuity.userdetails.LoginUserDetail;
import com.liuli.boot.security.jwt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = httpServletRequest.getHeader("token");

        // 如果没有token
        if (!StringUtils.hasText(token)) {
            // 没法获取到认证信息，放行后，后续过滤器鉴权会拦截请求
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return ;  //防止filter响应时执行后面代码
        }

        // 解析token
        Claims claims = JwtUtils.parseJwtToken(token);
        if (claims == null) {
            throw  new AccountExpiredException("用户登录已失效");
        }

        // 获取用户ID
        Integer userId =  (Integer)claims.get("userId");

        // 从缓存中获取用户信息
        LoginUserDetail user =  (LoginUserDetail) redisCache.get("login:" + userId);

        //设置SecurityContext
        SecurityContext context = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());
        context.setAuthentication(authentication);

        filterChain.doFilter(httpServletRequest, httpServletResponse);

        log.info("over.....");
    }
}
