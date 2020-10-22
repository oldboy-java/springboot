package com.imooc.girl.handle;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.girl.common.Result;
import com.imooc.girl.common.ResultUtils;
import com.imooc.girl.enums.ResultEnum;
import com.imooc.girl.exception.GirlException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


/***
 * 定义全局异常
 */
@ControllerAdvice //声明定义全局控制器通知
@Slf4j
public class ExceptionHandle {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleException(AuthorizationException e, Model model) {

		// you could return a 404 here instead (this is how github handles 403, so the user does NOT know there is a
		// resource at that location)
		log.debug("AuthorizationException was thrown", e);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", HttpStatus.FORBIDDEN.value());
		map.put("message", "No message available");
		model.addAttribute("errors", map);

		return "error";
	}


	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Result<Object> handle(Exception e){
		if(e instanceof GirlException) {
			GirlException girlException = (GirlException) e;
			logger.error("出现错误，错误码={},错误消息={}", girlException.getCode(), girlException.getMessage());
			return ResultUtils.error(girlException.getCode(), girlException.getMessage());
		}else {
			logger.error("出现错误，错误码={},错误消息={}", ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
			return ResultUtils.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}


}
