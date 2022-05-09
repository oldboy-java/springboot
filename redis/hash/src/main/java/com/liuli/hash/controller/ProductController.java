package com.liuli.hash.controller;

import com.alibaba.fastjson.JSON;
import com.liuli.hash.entity.Product;
import com.liuli.hash.utils.MapUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/v1/products")
@Api(value = "商品API", tags = {"商品"})
public class ProductController {
    private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping
    @ApiOperation(value = "添加商品")
    public  void addProduct(@RequestBody @ApiParam(value ="商品信息" ) Product product) {
        // 先插入数据库 。。。

        // 模拟数据库插入后返回商品ID
        Long productId = new Random().nextLong();

        // 将商品转成Map
        product.setId(productId);
        Map<String, Object> obj = MapUtils.objectToMap(product);

        // 计算Key
        String  key = computeKey(product.getId());

        // 写入redis
        redisTemplate.opsForHash().putAll(key, obj);

    }


    @GetMapping ("/{productId}")
    @ApiOperation(value = "查看商品")
    public  Product getProduct(@PathVariable @ApiParam(value ="商品Id" ) Long productId) {
        // 计算key
       String key = computeKey(productId);

       // 获取产品信息
        Map entries = redisTemplate.opsForHash().entries(key);
        return JSON.parseObject(JSON.toJSONString(entries), Product.class);
    }

    @PutMapping ("/{productId}/change-price")
    @ApiOperation(value = "调整商品价格")
    public  void changeProductPrice(@PathVariable @ApiParam(value ="商品Id" ) Long productId,
                                    @ApiParam(value ="商品调整价格" ) @RequestParam BigDecimal  price) {
        // 计算key
        String key = computeKey(productId);

        // 获取商品信息
        Map entries = redisTemplate.opsForHash().entries(key);

        BigDecimal oldPrice = (BigDecimal) entries.get("price");
        // 增加价格
        oldPrice =  oldPrice.add(price);

        // 修改价格
//        redisTemplate.opsForHash().increment(key, "price", price); //支持整形、double、long
        redisTemplate.opsForHash().put(key, "price", oldPrice);
    }

    private String computeKey(Long productId) {
        return "product:" + productId;
    }
}
