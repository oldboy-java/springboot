package com.imooc.girl.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Service
public class UserInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String  url = request.getRequestURL().toString();
		if(url.endsWith(".html")) {
			System.err.println("url="+url);
			return true;
		}
		return super.preHandle(request, response, handler);
	}
	
}
