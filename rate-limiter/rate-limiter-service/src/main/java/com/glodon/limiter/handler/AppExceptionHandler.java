package com.glodon.limiter.handler;

import com.glodon.limiter.exception.RateLimitException;
import com.glodon.limiter.utils.Result;
import com.glodon.limiter.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/***
 * 定义全局异常
 */
@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

	@ExceptionHandler(value= RateLimitException.class)
	@ResponseBody
	public Result handle(RateLimitException ex){
		log.error("出现错误，错误码={},错误消息={}", ex.getCode(), ex.getMessage());
		return ResultUtils.error(ex.getCode(), ex.getMessage());
	}


}
