package com.liuli.publish.subcribe;

import com.liuli.publish.subscribe.RedisPublishSubscribeApplication;
import com.liuli.publish.subscribe.model.User;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisPublishSubscribeApplication.class)
public class MessageTests {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送字符串消息
     */
    @Test
    @SneakyThrows
    public void sendStringMessage(){
        for(int i = 0;i < 10; i++) {
            redisTemplate.convertAndSend("SPORT.FOOTBALL", "This is message " + i);
        }
        TimeUnit.MINUTES.sleep(5);
    }

    /**
     * 发送用户对象消息
     */
    @Test
    @SneakyThrows
    public void sendUserMessage(){
        for(int i = 0;i < 10; i++) {
            User user = User.builder().name("张安" + i).age(100 + i).build();
            redisTemplate.convertAndSend("SPORT.FOOTBALL", user);
        }
        for(int i =0;i < 10; i++) {
            User user = User.builder().name("李安" + i).age(100 + i).build();
            redisTemplate.convertAndSend("SPORT.BASKETBALL", user);
        }
        TimeUnit.MINUTES.sleep(5);
    }
}
