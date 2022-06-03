package com.liuli.block.queue.consumer;

import com.alibaba.fastjson.JSON;
import com.liuli.block.queue.model.Weather;
import com.liuli.publish.subscribe.model.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 具体的消费者，定义真正消息处理逻辑、消费队列名称、等待时间
 */
@Component
@Slf4j
public class WeatherMsgConsumer extends AbstractRedisMsgConsumer {

    private static  final  String WEATHER_MSQ_QUEUE = "WEATHER_QUEUE";
    private static  final  String WEATHER_MSQ_BACKUP_QUEUE = "WEATHER_MSQ_BACKUP_QUEUE";

    @Override
    public Long getMsgWaitTime() {
        return 60L;
    }

    @Override
    public String getQueueName() {
        return WEATHER_MSQ_QUEUE;
    }

    @Override
    public String getBackupQueueName() {
        return WEATHER_MSQ_BACKUP_QUEUE;
    }

    @SneakyThrows
    @Override
    public void doConsumeMsg(Object message) {
        if (message instanceof Weather) {
            Weather weather = (Weather)message;
            log.info("WeatherMsgConsumer receive message = {}", JSON.toJSONString (weather));
        }
    }
}
