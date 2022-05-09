package com.liuli.boot.sharding;

import static org.junit.Assert.assertTrue;

import com.liuli.boot.sharding.entity.User;
import com.liuli.boot.sharding.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})  //指定启动类
public class AppTest {

    @Autowired
    UserMapper userMapper;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void insertUser() {
        for (int i = 0; i < 4; i++) {
            User user = new User();
            user.setUserName("z000" + i);
            user.setPassword("z000" + i);
            userMapper.insertUser(user);
        }
    }


    @Test
    public void listUser() {
        List<User> orders = userMapper.lisUsers(Arrays.asList(new Long[]{730559667335331840L, 730559666861375489L}));
        System.err.println(orders);

    }

}
