package com.liuli.redis.list.controller;

import com.liuli.redis.list.constants.RedisCacheKeyConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1/red-packets")
@Api(value = "抢红包API", tags = "红包")
@Slf4j
public class RedPacketController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DefaultRedisScript defaultRedisScript;

    /**
     * 包红包
     * @param total
     * @param count
     * @return
     */
    @ApiOperation(value = "包红包", tags = "红包")
    @PostMapping("/set")
    public boolean setRedPacket(@ApiParam(value = "总金额") @RequestParam(name = "total") Integer total,
                                                         @ApiParam(value = "红包个数")@RequestParam(name="count") Integer count) {
        // 拆解红包
        Integer[] packets = splitRedPacket(total, count);

        // 生成红包ID
        Long redPacketId = getRedPacketId();

        // 使用List存储红包
        String redPacketKey = String.format(RedisCacheKeyConstant.RED_PACKET_LIST_KEY, redPacketId);
        redisTemplate.opsForList().leftPushAll(redPacketKey, packets);

        // 设置3天过期
        redisTemplate.expire(redPacketKey, 3, TimeUnit.DAYS);
        // 写入DB // TODO

        log.info("拆解红包={}={}", redPacketId,  packets);
        return Boolean.TRUE;
    }


    /**
     * 抢红包
     * @param userId  用户ID
     * @param redPacketId 红包ID
     * @return  -1 抢购完   -2 用户已抢过红包 ，其他值代表抢到红包金额
     */
    @ApiOperation(value = "抢红包", tags = "红包")
    @PostMapping("/{redPacketId}/rob")
    public Integer robRedPacket(@ApiParam(value = "总金额") @RequestParam(name = "userId") Long userId,
                                @ApiParam(value = "红包Id")@PathVariable(name="redPacketId") Integer redPacketId) {
       //1、 验证用户是否已经抢过红包
        // 这里判断与弹出红包不是原子操作，多线程存在并发问题，存在一个人抢多个红包问题
        String redPacketRobHashKey = String.format(RedisCacheKeyConstant.RED_PACKET_ROB_HASH_KEY, redPacketId);
        Object robInfo = redisTemplate.opsForHash().get(redPacketRobHashKey, String.valueOf(userId));
        if (robInfo == null) {
            // 2、从list队列中弹出一个红包
            Object obj = redisTemplate.opsForList().leftPop(String.format(RedisCacheKeyConstant.RED_PACKET_LIST_KEY, redPacketId));
            if (obj != null) {
                // 3、抢到的红包记录存储到用户已抢情况缓存中
                redisTemplate.opsForHash().put(redPacketRobHashKey, String.valueOf(userId), obj);
                log.info("用户={}抢到{}", userId, obj);

                // 抢红包记录异步入口 TODO
                return  (Integer) obj;
            }else {
                return -1;
            }
        }else {
            return -2;
        }
    }

    /**
     * 抢红包
     * @param userId  用户ID
     * @param redPacketId 红包ID
     * @return  -1 抢购完   -2 用户已抢过红包 ，其他值代表抢到红包金额
     */
    @ApiOperation(value = "抢红包", tags = "红包")
    @PostMapping("/{redPacketId}/rob2")
    public Long robRedPacket2(@ApiParam(value = "总金额") @RequestParam(name = "userId") Long userId,
                                @ApiParam(value = "红包Id")@PathVariable(name="redPacketId") Integer redPacketId) {
        Object  redPacketMoney = redisTemplate.execute(defaultRedisScript,
                Arrays.asList(new String[]{String.valueOf(redPacketId), String.valueOf(userId)}));

        return (Long) redPacketMoney;
    }


    /**
     * 获取红包ID
     * @return
     */
    private Long getRedPacketId() {
        return redisTemplate.opsForValue().increment(RedisCacheKeyConstant.RED_PACKET_ID);
    }



    /**
     * 拆解红包
     *  1、红包金额要被全部拆解完
     * 2、红包金额不能相差太大
     * @param total
     * @param count
     * @return
     */
    private Integer[] splitRedPacket(Integer total, Integer count) {
        int use = 0;
        Integer[] array = new Integer[count];
        Random random = new Random();
        for (int i = 0; i <count; i++) {
            // 最后一个红包为剩余金额
            if (i == count -1) {
                array[i] = total -use;
            }else {
                // 2 红包随机金额浮动系数
                int avg = (total - use) * 2 / (count - i );
                array[i] = 1 + random.nextInt(avg -1);
                use = use + array[i];
            }
        }
        return array;
    }
}
