package com.imooc.girl.filter;

import com.alibaba.fastjson.JSONObject;
import com.imooc.girl.pojo.Girl;
import com.imooc.girl.service.GirlService;
import com.imooc.girl.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class AuthFilter implements Filter {

    @Autowired
    private GirlService girlService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Girl g = girlService.findGirl(1L);
        log.info("girl={}", g);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //获取URL
        String url = req.getRequestURL().toString();
        //获取请求参数串
        String query = req.getQueryString();

        //这里主要测试拦截html，如果访问html，直接报错500
        if (url.endsWith(".html") && !url.contains("login.html") && !url.contains("swagger-ui.html") && ! url.contains("/druid")) {
            //在Filter中获取Bean对象
            GirlService girlService2 = SpringContextUtils.getBean(GirlService.class);

            System.err.println("url=" + url + "?" + query + "&t=" + (girlService == girlService2 ? true : false));
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 401);
                jsonObject.put("message", "静态页面不能直接访问");

                response(resp, jsonObject);
                //	resp.sendRedirect("login.html");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        chain.doFilter(request, response);
    }

    private void response(HttpServletResponse resp, JSONObject jsonObject) throws Exception {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream out = null;
        try {
            out = resp.getOutputStream();
            out.write(jsonObject.toString().getBytes("utf-8"));
            out.flush(); //必须调用flush或者close方法，否则ContentType不起作用
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
	
	/*private void  response(HttpServletResponse resp,Object obj)  throws Exception{
		OutputStream out=null;
		resp.setContentType("application/json; charset=utf-8");
		try{
		    out = resp.getOutputStream();
		    out.write(JsonUtils.objectToJson(obj).getBytes("utf-8"));
		    out.flush();
		} finally{
		    if(out!=null){
		        out.close();
		    }
		}
	}*/

    @Override
    public void destroy() {

    }

}
