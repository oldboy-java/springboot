package com.imooc.girl.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.imooc.girl.dao.GirlRespository;
import com.imooc.girl.service.GirlService;
import com.imooc.girl.utils.SpringContextUtils;

public class AuthFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		//获取URL
		String  url = req.getRequestURL().toString();
		//获取请求参数串
		String query = req.getQueryString();
		
		//这里主要测试拦截html，如果访问html，直接报错500
		if(url.endsWith(".html") && !url.contains("login.html")) {
			//在Filter中获取Bean对象
			GirlService girlService = SpringContextUtils.getBean(GirlService.class);
			GirlService girlService2 = SpringContextUtils.getBean(GirlService.class);
			
			System.err.println("url="+url+"?"+query+"&t="+ (girlService == girlService2 ? true :false));
			try {
			//	response(resp);
				resp.sendRedirect("login.html");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		chain.doFilter(request, response);
	}

	private void response(HttpServletResponse resp)  throws Exception{
		
		resp.setContentType("application/json; charset=utf-8");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code",401);
		jsonObject.put("message", "静态页面不能直接访问");
		resp.getOutputStream().write(jsonObject.toJSONString().getBytes("UTF-8"));
	}
	
	@Override
	public void destroy() {
		
	}

}
