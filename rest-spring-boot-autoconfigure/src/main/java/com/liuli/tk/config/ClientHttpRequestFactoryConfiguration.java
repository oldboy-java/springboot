package com.liuli.tk.config;

import com.tk.httpclient.autoconfigure.HttpClientAutoConfiguration;
import org.apache.http.client.HttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;


/**
 * 根据配置参数动态决定实例化哪种ClientHttpRequestFactory
 *
 */
@Configuration
@EnableConfigurationProperties(RestTemplateProperties.class)
@AutoConfigureAfter(HttpClientAutoConfiguration.class)
public class ClientHttpRequestFactoryConfiguration {
	
    @Bean
    @ConditionalOnClass(OkHttp3ClientHttpRequestFactory.class)
    @ConditionalOnProperty(prefix = "tk.rest-template.client-http-request-factory", name = {"type"} ,havingValue = "okhttp3")
    public OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory(){
        return new OkHttp3ClientHttpRequestFactory();
    }

    @Bean
    @ConditionalOnClass(HttpClient.class)
    @ConditionalOnProperty(prefix = "tk.rest-template.client-http-request-factory", name = {"type"} ,havingValue = "httpclient")
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory(HttpClient httpClient){
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

}
