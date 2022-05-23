package com.liuli.boot.sharding;

import com.liuli.boot.sharding.mapper.ProductDao;
import com.liuli.boot.sharding.entity.ProductInfo;
import com.liuli.boot.sharding.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})  //指定启动类
public class AppTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductDao productDao;

    //添加商品
    @Test
    public void testCreateProduct(){
        for (int i=1;i<10;i++){
            ProductInfo productInfo = new ProductInfo();
            productInfo.setStoreInfoId(1L);//店铺id

            productInfo.setProductName("Java架构师"+i);//商品名称
            productInfo.setSpec("大号");
            productInfo.setPrice(new BigDecimal(60));
            productInfo.setRegionCode("110100");
            productInfo.setDescript("Java架构师不错！！！"+i);//商品描述
            productService.createProduct(productInfo);
        }

    }

    //查询商品
    @Test
    public void testQueryProduct(){
        List<ProductInfo> productInfos = productService.queryProduct(2, 5);
        System.out.println(productInfos);
    }

    //统计商品总数
    @Test
    public void testSelectCount(){
        int i = productDao.selectCount();
        System.out.println(i);
    }

    //分组统计商品
    @Test
    public void testSelectProductGroupList(){

        List<Map> maps = productDao.selectProductGroupList();

        System.out.println(maps);
    }


}
