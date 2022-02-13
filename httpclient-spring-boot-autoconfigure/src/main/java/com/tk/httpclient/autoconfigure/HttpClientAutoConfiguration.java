package com.tk.httpclient.autoconfigure;


import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({HttpClient.class})
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {

    private HttpClientProperties httpClientProperties;


    public HttpClientAutoConfiguration(HttpClientProperties httpClientProperties) {
        this.httpClientProperties = httpClientProperties;
    }


    @Bean
    public HttpClient httpClient() {
        RequestConfig.Builder custom = RequestConfig.custom();
        custom.setConnectTimeout(this.httpClientProperties.getConnectTimeout());
        custom.setSocketTimeout(this.httpClientProperties.getSocketTimeout());
        RequestConfig config = custom.build();

        HttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setUserAgent(this.httpClientProperties.getAgent())

                // 每个服务（域名）请求的最大连接数
                .setMaxConnPerRoute(this.httpClientProperties.getMaxConnPerRoute())

                // 总连接数
                .setMaxConnTotal(this.httpClientProperties.getMaxConnTotal())

                .build();

        // 不使用连接重用
//                .setConnectionReuseStrategy(new NoConnectionReuseStrategy()).build();

        // 默认使用长连接，提升性能

        return client;

    }

}
