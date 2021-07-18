package com.imooc.girl.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.girl.dao.GirlRespository;
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

import java.util.List;

@Service
public class GirlServiceImpl implements GirlService {

	@Autowired
	private GirlRespository girlRespository;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	@Cacheable(value = "girl", key = "#root.method.name", sync = true) //指定多个线程同时执行时使用本地同步锁，一定程度上解决缓存击穿问题
	public List<Girl> girlList() {
		return girlRespository.findAll();
	}

	@Override
	@Transactional //事务处理，出现异常自动回滚
	public Girl addGirl(Girl girl) throws Exception{
		 girl =  girlRespository.save(girl);
		 
		 //模拟发生异常
		 if(1==1) {
			 throw new RuntimeException("daaa"); 
		 }
		 return girl;
	}

	@Override
	public Girl updateGirl(Girl girl) {
		return girlRespository.save(girl);
	}

	@Override
	public Girl findGirl(Integer id) {
		String girl = redisTemplate.opsForValue().get(String.valueOf(id.intValue()));
		Girl g = null;
		// 这里仅仅是验证默认redis自动配置功能，所有直接使用StringRedisTemplate
		if (StringUtils.isEmpty(girl)) {
			 g =  girlRespository.getOne(id);
			redisTemplate.opsForValue().set(String.valueOf(id.intValue()), JSON.toJSONString(g));
		}else {
			g = JSON.parseObject(girl, Girl.class);
		}
		return g;
	}

	@Override
	@CacheEvict(value = "girl")
	public void deleteGirl(Integer id) {
		girlRespository.deleteById(id);
	}

	@Override
	public List<Girl> girlListByAge(Integer age) {
		return girlRespository.findByAge(age);
	}

	@Override
	public Integer getGirlAge(Integer id) throws Exception {
		Girl girl = girlRespository.getOne(id);
		Integer age  = girl.getAge();
		if(age < 10 ) {
			throw new GirlException(ResultEnum.PRIMARY_SCHOOL.getCode(),ResultEnum.PRIMARY_SCHOOL.getMessage());
		}else if( age > 10 && age <16) {
			throw new GirlException(ResultEnum.MIDDLE_SCHOOL.getCode(),ResultEnum.MIDDLE_SCHOOL.getMessage());
		}
		return age;
	}
	
	
}
