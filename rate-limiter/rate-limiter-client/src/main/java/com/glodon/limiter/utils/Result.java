package com.glodon.limiter.utils;

import lombok.Data;

/***
 * 响应结果
 * @author	刘力
 * @date	2017年10月15日上午11:08:08
 * @version 1.0
 */
@Data
public class Result<T> {
	private int code; //状态码
	private String msg; //消息
	private T data; //结果集
}
