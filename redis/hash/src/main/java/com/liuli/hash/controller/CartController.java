package com.liuli.hash.controller;

import com.alibaba.fastjson.JSON;
import com.liuli.hash.entity.CartDTO;
import com.liuli.hash.entity.CartItemDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/carts")
@Api(value = "购物车接口", tags = {"购物车"})
public class CartController {

    @Autowired
    RedisTemplate redisTemplate;

    private static  final String USER_CART_KEY = "cart:user:%s";


    /**
     * 选择某个商品加入购物车
     * @param cart
     */
    @PostMapping("/addProduct")
    public void addProduct(@RequestBody CartItemDTO cart) {
        // 构造购物车key
        String key = String.format(USER_CART_KEY, cart.getUserId());

        // 判断用户的购物车是否存在
        boolean hasKey = redisTemplate.opsForHash().getOperations().hasKey(key);
        if (hasKey) {
            redisTemplate.opsForHash().put(key, cart.getProductId().toString(), cart.getAccount());
        }else {
            redisTemplate.opsForHash().put(key, cart.getProductId().toString(), cart.getAccount());
            redisTemplate.expire(key, 90, TimeUnit.DAYS);
        }
    }


    /**
     * 在购物车页面选择某个商品，通过+-操作修改商品数量
     * @param cart
     */
    @PutMapping("/updateProductAccount")
    public void updateProductAccount(@RequestBody CartItemDTO cart) {
        // 构造购物车key
        String key = String.format(USER_CART_KEY, cart.getUserId());

        //修改购物车中商品数量
        redisTemplate.opsForHash().put(key, cart.getProductId().toString(), cart.getAccount());
    }


    /**
     * 商品商品
     * @param userId
     * @param productId
     */
    @DeleteMapping("/deleteProduct")
    public void deleteProduct(@RequestParam("userid") Long userId, @RequestParam("productId") Long productId) {
        // 构造购物车key
        String key = String.format(USER_CART_KEY, userId);

        // 删除购物车中某件商品
        redisTemplate.opsForHash().delete(key, productId.toString());
    }


    /**
     * 购物车商品列表
     * @param userId
     */
    @GetMapping("/items")
    public CartDTO getCartItems(@RequestParam("userid") Long userId) {
        CartDTO.CartDTOBuilder builder = CartDTO.builder();
        // 构造购物车key
        String key = String.format(USER_CART_KEY, userId);

        //获取购物车商品总数
        Long total = redisTemplate.opsForHash().size(key);
        builder.total(total);

            // entries == hgetall命令
        Map<String, Integer> map  = redisTemplate.opsForHash().entries(key);
        List<CartItemDTO> items = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            CartItemDTO cartItemDTO = CartItemDTO.builder().productId(Long.parseLong(entry.getKey()))
                                                                    .account(map.get(entry.getKey())).build();
            items.add(cartItemDTO);
        }
        builder.items(items);
        return builder.build();
    }

}
