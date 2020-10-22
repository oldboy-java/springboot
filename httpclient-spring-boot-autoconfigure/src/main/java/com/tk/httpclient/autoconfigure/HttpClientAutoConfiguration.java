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


    public  HttpClientAutoConfiguration(HttpClientProperties httpClientProperties) {
        this.httpClientProperties = httpClientProperties;
    }


    @Bean
    public  HttpClient httpClient(){
        RequestConfig config =  RequestConfig.custom().setConnectTimeout(this.httpClientProperties.getConnectTimeout())
                .setSocketTimeout(this.httpClientProperties.getSocketTimeout()).build();

        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).setUserAgent(this.httpClientProperties.getAgent())
                .setMaxConnPerRoute(this.httpClientProperties.getMaxConnPerRoute()).setMaxConnTotal(this.httpClientProperties.getMaxConnTotal())
                .setConnectionReuseStrategy(new NoConnectionReuseStrategy()).build();

        return client;

    }

}
