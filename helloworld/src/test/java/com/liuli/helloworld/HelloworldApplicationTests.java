package com.liuli.helloworld;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloworldApplicationTests {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate restTemplate;


    @Before
    public void setup() throws MalformedURLException {
        this.base = new URL("http://localhost:" + port + "/helloworld/hello");
    }

    @Test
    public void hello() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(base.toString(), String.class);
        Assert.assertEquals(responseEntity.getBody(), "hello world!");
    }

}
