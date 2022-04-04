package com.imooc.girl.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.imooc.girl.mapper.GirlMapper;
import com.imooc.girl.enums.ResultEnum;
import com.imooc.girl.exception.GirlException;
import com.imooc.girl.pojo.Girl;
import com.imooc.girl.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Wrapper;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class GirlServiceImpl extends ServiceImpl<GirlMapper, Girl> implements GirlService {

    @Autowired
    private GirlMapper mapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    @Cacheable(value = "girl", key = "#root.method.name", sync = true) //指定多个线程同时执行时使用本地同步锁，一定程度上解决缓存击穿问题
    public List<Girl> girlList() {
        EntityWrapper wrapper = new EntityWrapper<>();
        return mapper.selectList(wrapper);
    }

    @Override
    @Transactional//事务处理，出现异常自动回滚
    public Girl addGirl(Girl girl) throws Exception {
         mapper.insert(girl);

//        //模拟发生异常,出现异常自动回滚
//        if (1 == 1) {
//            throw new RuntimeException("daaa");
//        }
        TimeUnit.SECONDS.sleep(20);
        return girl;
    }

    @Override
    public Integer updateGirl(Girl girl) {
        return mapper.updateById(girl);
    }

    @Override
    public Girl findGirl(Long id) {
        String girl = redisTemplate.opsForValue().get(String.valueOf(id.intValue()));
        Girl g = null;
        // 这里仅仅是验证默认redis自动配置功能，所有直接使用StringRedisTemplate
        if (StringUtils.isEmpty(girl)) {
            g = mapper.selectById(id);
            redisTemplate.opsForValue().set(String.valueOf(id.intValue()), JSON.toJSONString(g));
        } else {
            g = JSON.parseObject(girl, Girl.class);
        }
        return g;
    }

    @Override
    @CacheEvict(value = "girl")
    public void deleteGirl(Long id) {
        mapper.deleteById(id);
    }



}
