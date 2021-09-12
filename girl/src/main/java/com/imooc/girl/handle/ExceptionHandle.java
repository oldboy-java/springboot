package com.imooc.girl.handle;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.imooc.girl.common.Result;
import com.imooc.girl.common.ResultUtils;
import com.imooc.girl.enums.ResultEnum;
import com.imooc.girl.exception.GirlException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/***
 * 定义全局异常
 */
@ControllerAdvice //声明定义全局控制器通知
@Slf4j
public class ExceptionHandle {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Result<Object> handle(Exception e){
		if(e instanceof GirlException) {
			GirlException girlException = (GirlException) e;
			logger.error("出现错误，错误码={},错误消息={}", girlException.getCode(), girlException.getMessage());
			return ResultUtils.error(girlException.getCode(), girlException.getMessage());
		}else {
			// 如果是Sentinel异常
			if (BlockException.isBlockException(e)) {
				logger.error("出现错误，错误码={},错误消息={}", 500, e.getClass().getSimpleName());
				return ResultUtils.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getClass().getSimpleName());
			}
			logger.error("出现错误，错误码={},错误消息={}", ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
			return ResultUtils.error(ResultEnum.UNKNOWN_ERROR.getCode(), e.getMessage());
		}
	}


}
