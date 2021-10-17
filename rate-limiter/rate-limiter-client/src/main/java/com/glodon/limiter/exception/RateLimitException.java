package com.glodon.limiter.exception;

import lombok.Data;

@Data
public class RateLimitException extends  RuntimeException{

    private Integer code;

    public  RateLimitException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
