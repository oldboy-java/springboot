package com.liuli.hash.controller;

import com.liuli.hash.entity.CartDTO;
import com.liuli.hash.entity.CartItemDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/carts/cookie")
@Api(value = "未登录购物车接口", tags = {"未登录购物车"})
public class CookieCartController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    private static  final String COOKIE_CART_KEY = "cart:cookie:%s";
    private static  final String USER_CART_KEY = "cart:user:%s";


    private Long  getCartIdFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for(Cookie cookie: cookies) {
                if (cookie.getName().equals("cartId")) {
                    return  Long.parseLong(cookie.getValue());
                }
            }
        }
        return 0L;
    }

    /**
     * 选择某个商品加入购物车
     * @param cart
     */
    @PostMapping("/addProduct")
    public void addProduct(@RequestBody CartItemDTO cart) {
        // 构造购物车key
        String key = computeKey();

        // 判断用户的购物车是否存在
        boolean hasKey = redisTemplate.opsForHash().getOperations().hasKey(key);
        if (hasKey) {
            redisTemplate.opsForHash().put(key, cart.getProductId().toString(), cart.getAccount());
        }else {
            redisTemplate.opsForHash().put(key, cart.getProductId().toString(), cart.getAccount());
            redisTemplate.expire(key, 90, TimeUnit.DAYS);
        }
    }

    private String computeKey() {
        Long cartId = getCartIdFromCookie(request);
        if (cartId ==0) {
            cartId = new Random().nextLong();
            Cookie cookie = new Cookie("cartId", String.valueOf(cartId));
            response.addCookie(cookie);
        }
        return String.format(COOKIE_CART_KEY, cartId);
    }


    /**
     * 在购物车页面选择某个商品，通过+-操作修改商品数量
     * @param cart
     */
    @PutMapping("/updateProductAccount")
    public void updateProductAccount(@RequestBody CartItemDTO cart) {
        // 构造购物车key
        String key = computeKey();

        //修改购物车中商品数量
        redisTemplate.opsForHash().put(key, cart.getProductId().toString(), cart.getAccount());
    }


    /**
     * 商品商品
     * @param productId
     */
    @DeleteMapping("/deleteProduct")
    public void deleteProduct( @RequestParam("productId") Long productId) {
        // 构造购物车key
        String key = computeKey();

        // 删除购物车中某件商品
        redisTemplate.opsForHash().delete(key, productId.toString());
    }


    /**
     * 购物车商品列表
     */
    @GetMapping("/items")
    public CartDTO getCartItems() {
        CartDTO.CartDTOBuilder builder = CartDTO.builder();
        // 构造购物车key
        String key = computeKey();

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



    /**
     * 合并购物车
     */
    @PutMapping("/mergeCart")
    public void updateProductAccount(@RequestParam("userid") Long userId) {
        // 构造未登录购物车key
        String cookieKey = computeKey();
        Map cookieCarts = redisTemplate.opsForHash().entries(cookieKey);

        //构造用户购物车key
        String userKey = String.format(USER_CART_KEY, userId);

        //将未登录购物车合并到登录用户购物车中
        // 如果已登录购物车和未登录购物车中有有相同商品，下面合并有问题，需要对商品数量累加
        redisTemplate.opsForHash().putAll(userKey, cookieCarts);

        // 删除未登录购物车
        redisTemplate.delete(cookieKey);

        // 删除未登录购物车cookie
        Cookie cookie = new Cookie("cartId","");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

    }
}
