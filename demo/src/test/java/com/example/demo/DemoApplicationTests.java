package com.example.demo;

import com.example.demo.enums.CupSize;
import com.example.demo.mapper.GirlMapper;
import com.example.demo.pojo.Girl;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {

    @Autowired
    HttpClient httpClient;

    @Autowired
    private GirlMapper mapper;

    @Autowired
    @Qualifier("batchSqlSession")
    private SqlSession sqlSession;

    @Test
    public void contextLoads() {
        HttpGet httpGet = new HttpGet("http://www.qq.com");
        try {
            String html = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
            log.info("{}", html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddBatch() {
        Long start = System.currentTimeMillis();
        List<Girl> girls = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            girls.add(new Girl(i, CupSize.LARGE));
        }


        mapper.addByBatch(girls);

        Long end = System.currentTimeMillis();

        log.info("costs " + (end - start) + "  millisecond");
    }


    @Test
    public void testAddBatch2() {
        Long start = System.currentTimeMillis();

        GirlMapper mapper2 = sqlSession.getMapper(GirlMapper.class);
        for (int i = 0; i < 10000; i++) {
            mapper2.add(new Girl(i, CupSize.LARGE));
        }

        Long end = System.currentTimeMillis();

        log.info("costs " + (end - start) + "  millisecond");
    }


}
