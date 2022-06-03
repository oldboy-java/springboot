package com.liuli.publish.subscribe.listener;

import com.alibaba.fastjson.JSON;
import com.liuli.publish.subscribe.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;


/**
 * 自定义消息监听器，作为为消息的订阅者，订阅单个指定频道中的消息
 */
@Component
@Slf4j
public class Sport2Listener implements TopicListener {

    private static final String SPORT_FOOTBALL_TOPIC = "SPORT.FOOTBALL";

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
            log.info("Sport2Listener receive message = {}, topic={}", (String)value,topic);
        }
        if (value instanceof  User) {
            log.info("Sport2Listener receive message = {}, topic={}", JSON.toJSONString ((User)value),topic);
        }


    }

    /**
     * 定义ChannelTopic，只订阅单个Channel的消息
     * @return
     */
    @Override
    public Topic getTopic() {
        return new ChannelTopic(SPORT_FOOTBALL_TOPIC);
    }
}
