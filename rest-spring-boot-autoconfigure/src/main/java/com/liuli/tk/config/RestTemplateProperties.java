package com.liuli.tk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tk.rest-template")
@Data
public class RestTemplateProperties {

	private TkClientHttpRequestFactory  clientHttpRequestFactory = new TkClientHttpRequestFactory();
	

	 /**
     *  类型： loadBalanced、direct
     */
    private String type;
    
    
    /**
     *  定义HttpRequestFactory配置
     */
	@Data
    public  static class TkClientHttpRequestFactory {
        /**
         *  请求工厂类型
         */
        private String type;
    }
}
