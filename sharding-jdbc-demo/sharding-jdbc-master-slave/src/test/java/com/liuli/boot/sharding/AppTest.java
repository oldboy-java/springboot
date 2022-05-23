package com.liuli.boot.sharding;

import com.liuli.boot.sharding.entity.User;
import com.liuli.boot.sharding.mapper.UserMapper;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(30 + i);
            user.setUserName("张三" + i);
            userMapper.insertUser(user);
        }

        // 强制读取主库
        HintManager.getInstance().setMasterRouteOnly();

        List<User> orders = userMapper.listUsers(Arrays.asList(new Long[]{735175301822152704L}));
        System.err.println(orders);
    }


    @Test
    public void listUsers() {
        List<User> orders = userMapper.listUsers(Arrays.asList(new Long[]{735175301822152704L}));
        System.err.println(orders);

    }


}
