package com.liuli.publish.subscribe.listener;

import com.alibaba.fastjson.JSON;
import com.liuli.publish.subscribe.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;


/**
 * 自定义消息监听器，作为为消息的订阅者，订阅符合匹配模式的多个频道中的消息
 */
@Component
@Slf4j
public class SportListener implements TopicListener {

    private static final String SPORT_PATTERN_TOPIC = "SPORT.*";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
        Object value =valueSerializer.deserialize(body);
        String topic = redisTemplate.getStringSerializer().deserialize(channel);
        if (value instanceof  String) {
            log.info("SportListener receive message = {}, topic={}", (String)value,topic);
        }
        if (value instanceof  User) {
            log.info("SportListener receive message = {}, topic={}", JSON.toJSONString ((User)value),topic);
        }
    }

    /**
     *  定义PatternTopic，可以订阅多个频道的消息
     * @return
     */
    @Override
    public Topic getTopic() {
        return new PatternTopic(SPORT_PATTERN_TOPIC);
    }
}
