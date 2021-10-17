package com.glodon.limiter.utils;

public class ResultUtils {

	public static <T>  Result  success(T data) {
		Result<T> result = new Result<T>();
		result.setCode(200);
		result.setMsg(null);
		result.setData(data);
		return result;
	}
	
	public static Result success() {
		return success(null);
	}
	
	public static Result error(Integer code,String message) {
		Result result = new Result<>();
		result.setCode(code);
		result.setMsg(message);
		return result;
	}
}
