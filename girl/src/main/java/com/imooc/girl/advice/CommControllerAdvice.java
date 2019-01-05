package com.imooc.girl.advice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 全局控制器通知
 */
@ControllerAdvice(basePackages= {"com.imooc.girl.controller"}) //标识控制器通知，并且指定拦截的控制器包
public class CommControllerAdvice {

	//定义HTTP对应参数处理规则
	@InitBinder  //也可以放在特定的控制器中，针对特定控制器起作用，这里对所有控制器起作用
	public void initBinder(WebDataBinder binder) {
		//针对日期类型的格式化
		binder.registerCustomEditor(Date.class, 
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
	}
	
	
}
