package com.tk.httpclient.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.httpclient")
public class HttpClientProperties {
    /**
     * 连接超时时间毫秒
     */
    private Integer connectTimeout = 1000;

    /**
     * 读超时时间
     */
    private  Integer socketTimeout = 10000;

    /**
     * agent
     */
    private String agent = "agent";

    /**
     * 针对一个域名同时间正在使用的最多的连接数
     */
    private  Integer maxConnPerRoute = 10;

    /**
     * 同时间正在使用的最多的连接数
     */
    private Integer maxConnTotal = 50;

}
