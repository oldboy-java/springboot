package com.example.demo.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.pojo.JSONResult;

@ControllerAdvice
public class DemoExceptionHandler {

	private static final String DEMO_ERROR_VIEW="error"; //定义统一错误处理页面
	
	@ExceptionHandler(value=Exception.class) //定义捕获异常类型
	@ResponseBody  //针对ajax异常返回Json需加改注解，否则报模版找不到异常
	public Object errorHandler(HttpServletRequest request,HttpServletResponse response,Exception e) throws Exception {
		e.printStackTrace();
		
		if(isAjax(request)) {
			return JSONResult.errorException(e.getMessage());
		}else {
			ModelAndView mav = new ModelAndView();
			mav.addObject("exception", e);
			mav.addObject("url",request.getRequestURL());
			mav.setViewName(DEMO_ERROR_VIEW);
			return mav;
		}
	}
	
	
	/***
	 * @Title: DemoExceptionHandler.java
	 * @Package com.example.demo.exception.handler
	 * @Description 判断是否是Ajax请求
	 * Copyright: Copyright (c) 2017
	 * Company:FURUIBOKE.SCIENCE.AND.TECHNOLOGY
	 * @author xxxxx
	 * @date 2017年12月3日 下午1:40:39
	 * @version V1.0
	 * @param request
	 * @return
	 */
	public boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With")!=null && "XMLHttpRequest"
				.equals( request.getHeader("X-Requested-With").toString()));
		
	}
}
