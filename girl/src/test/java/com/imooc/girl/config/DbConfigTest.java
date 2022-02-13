package com.imooc.girl.config;

import com.imooc.girl.pojo.Db;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles({"dev","prod"}) 激活dev和prod环境
@ActiveProfiles({"prod"}) //只激活prod环境
public class DbConfigTest {

    @Autowired
    @Qualifier("db")
    private Db db;

    @Test
    public void getDbest() throws Exception {
//		Assert.assertEquals(db.getUser(), "zhangsan");
    }
}
