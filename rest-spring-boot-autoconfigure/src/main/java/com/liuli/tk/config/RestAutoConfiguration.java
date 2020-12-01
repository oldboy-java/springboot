package com.liuli.tk.config;

import com.liuli.tk.converter.FastJsonHttpMessageConverter5;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Configuration
@ConditionalOnClass(RestTemplate.class)
@EnableConfigurationProperties(RestTemplateProperties.class)
@AutoConfigureAfter(ClientHttpRequestFactoryConfiguration.class)
public class RestAutoConfiguration {

	/**
	 * 客户端负载均衡的RestTemplate，且使用OkHttp3ClientHttpRequestFactory
	 * @param okHttp3HttpRequestFactory
	 * @return
	 */
	@Bean
	@ConditionalOnBean(OkHttp3ClientHttpRequestFactory.class)
	@LoadBalanced
	@ConditionalOnProperty(prefix = "tk.rest-template", name = {"type"} ,havingValue = "loadBalanced")
	public RestTemplate okHttp3LbRestTemplate(OkHttp3ClientHttpRequestFactory okHttp3HttpRequestFactory) {
		RestTemplate  okHttp3LbRestTemplate = new RestTemplate(okHttp3HttpRequestFactory);

		okHttp3LbRestTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		okHttp3LbRestTemplate.getMessageConverters().add(1, new FastJsonHttpMessageConverter5());
		return  okHttp3LbRestTemplate;
	}

	
	/**
	 * 客户端负载均衡的RestTemplate，且使用HttpComponentsClientHttpRequestFactory
	 * @param HttpComponentsClientHttpRequestFactory
	 * @return
	 */
	@Bean
	@ConditionalOnBean(HttpComponentsClientHttpRequestFactory.class)
	@LoadBalanced
	@ConditionalOnProperty(prefix = "tk.rest-template", name = {"type"} ,havingValue = "loadBalanced")
	public RestTemplate  httpClientLbRestTemplate(HttpComponentsClientHttpRequestFactory httpComponentClientRequestFactory) {
		RestTemplate  okHttp3LbRestTemplate = new RestTemplate(httpComponentClientRequestFactory);

		okHttp3LbRestTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		okHttp3LbRestTemplate.getMessageConverters().add(1, new FastJsonHttpMessageConverter5());
		return  okHttp3LbRestTemplate;
	}


	/**
	 * 直连RestTemplate，且使用OkHttp3ClientHttpRequestFactory
	 * @param okHttp3HttpRequestFactory
	 * @return
	 */
	@Bean
	@ConditionalOnBean(OkHttp3ClientHttpRequestFactory.class)
	@ConditionalOnProperty(prefix = "tk.rest-template", name = {"type"} ,havingValue = "direct")
	public RestTemplate okHttpRestTemplate(OkHttp3ClientHttpRequestFactory okHttp3HttpRequestFactory) {
		RestTemplate  okHttp3LbRestTemplate = new RestTemplate(okHttp3HttpRequestFactory);

		// 默认StringHttpMessageConverter使用的是ISO-8859-1编码，这里设置为uTF-8
		okHttp3LbRestTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		
		// 默认采用Jackson,这里使用fastJson。序列化速度快
		okHttp3LbRestTemplate.getMessageConverters().add(1, new FastJsonHttpMessageConverter5());
		return  okHttp3LbRestTemplate;
	}


	/**
	 * 直连RestTemplate，且使用HttpComponentsClientHttpRequestFactory
	 * @param httpComponentClientRequestFactory
	 * @return
	 */
	@Bean
	@ConditionalOnBean(HttpComponentsClientHttpRequestFactory.class)
	@ConditionalOnProperty(prefix = "tk.rest-template", name = {"type"} ,havingValue = "direct")
	public RestTemplate  httpClientRestTemplate(HttpComponentsClientHttpRequestFactory httpComponentClientRequestFactory) {
		RestTemplate  okHttp3LbRestTemplate = new RestTemplate(httpComponentClientRequestFactory);

		okHttp3LbRestTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		okHttp3LbRestTemplate.getMessageConverters().add(1, new FastJsonHttpMessageConverter5());
		return  okHttp3LbRestTemplate;
	}

}
