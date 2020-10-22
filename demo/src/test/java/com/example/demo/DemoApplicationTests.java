package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {

	@Autowired
	HttpClient httpClient;

	@Test
	public void contextLoads() {
		HttpGet httpGet = new HttpGet("http://www.qq.com");
		try {
			String  html = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
			log.info("{}", html);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
