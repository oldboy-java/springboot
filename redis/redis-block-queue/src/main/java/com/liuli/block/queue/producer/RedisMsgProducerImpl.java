package com.liuli.block.queue.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis消息生产者，实现消息费发送（使用LeftPush命令发送消息）
 */
@Component
public  class RedisMsgProducerImpl implements RedisMsgProducer {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public  void send(RedisMqMessage message) {
        redisTemplate.opsForList().leftPush(message.getQueue(), message.getData());
    }

}
