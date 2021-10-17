package com.glodon.shorturl.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.glodon.shorturl.entity.ShortUrlEntity;
import com.glodon.shorturl.mapper.ShortUrlMapper;
import com.glodon.shorturl.service.ShortUrlService;
import com.glodon.shorturl.utils.ShortUrlGenerator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class ShortUrlServiceImpl  extends ServiceImpl<ShortUrlMapper, ShortUrlEntity> implements ShortUrlService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static  final String SHORT_URL_KEY = "short:url";

    @Override
    public String generateShortUrl(String sourceUrl, Long validAccessTime) {
        // 需要考虑同一个地址返回生成短地址情况，返回生成进行更新处理
        Date currentDate = new Date();
        Date expireTime = validAccessTime == -1 ?
                new Date("9999-12-31") : new Date(currentDate.getTime() + validAccessTime);
        String shortCode = ShortUrlGenerator.shortUrl(sourceUrl,"")[0];
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(sourceUrl).build();
        ShortUrlEntity shortUrl = ShortUrlEntity.builder().baseUrl(uriComponents.getScheme() + "://" + uriComponents.getHost())
                .suffixUrl(uriComponents.getPath()).fullUrl(sourceUrl)
                .expireTime(expireTime)
                .shortCode(shortCode)
                .createTime(currentDate)
                .build();
        this.insert(shortUrl);

      // 记录缓存
      stringRedisTemplate.opsForValue().set(SHORT_URL_KEY+":" +shortCode, JSON.toJSONString(shortUrl),
              validAccessTime, TimeUnit.MILLISECONDS);

      return shortCode;
    }

    @SneakyThrows
    @Override
    public String getSourceUrl(String shortCode) {
        String sourceUrlInfo = stringRedisTemplate.opsForValue().get(SHORT_URL_KEY+":" + shortCode);

        // 可增加异常处理
        if (StringUtils.isEmpty(sourceUrlInfo)) {
            // 抛出异常，可以自定义异常，后续可完善
            throw new Exception("该地址不存在：http://127.0.0.1:8080/" +shortCode);
        }else {
            return JSON.parseObject(sourceUrlInfo, ShortUrlEntity.class).getFullUrl();
        }

    }
}
