package com.liuli.boot.sharding;

import static org.junit.Assert.assertTrue;

import com.liuli.boot.sharding.entity.Dict;
import com.liuli.boot.sharding.entity.Order;
import com.liuli.boot.sharding.mapper.DictMapper;
import com.liuli.boot.sharding.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})  //指定启动类
public class AppTest {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    private DictMapper dictMapper;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void insertOrder() {
        for (int i = 0; i < 4; i++) {
            Order order = new Order();
            order.setPrice(new BigDecimal(123));
            order.setUserId(1000 + new Long(i));
            order.setStatus("下单成功");
            orderMapper.insertOrder(order);
        }
    }


    @Test
    public void listOrder() {
        // 订单ID位于同一张表t_order_1,执行一次查询
        List<Order> orders = orderMapper.listOrders(Arrays.asList(new Long[]{730110660179918848L, 730110661794725888L}));
        System.err.println(orders);

        // 订单ID位于不同表t_order_1, t_order_2，分别执行t_order_1、t_order_2表的查询，最终合并
        orders = orderMapper.listOrders(Arrays.asList(new Long[]{730110660179918848L, 730110659261366273L}));
        System.err.println(orders);
    }


    @Test
    public  void insertDict(){
        Dict dict = new Dict();
        dict.setCode("z009");
        dict.setValue("8888");
        dictMapper.insertDict(dict);
    }

    @Test
    public void deleteDict(){
        dictMapper.deleteDict(730565193192439809L);
    }
}
