package com.liuli.block.queue.consumer;

import com.alibaba.fastjson.JSON;
import com.liuli.publish.subscribe.model.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 具体的消费者，定义真正消息处理逻辑、消费队列名称、等待时间
 */
@Component
@Slf4j
public class SportMsgConsumer extends AbstractRedisMsgConsumer {

    private static  final  String SPORT_MSQ_QUEUE = "SPORT_QUEUE";
    private static  final  String SPORT_MSQ_BACKUP_QUEUE = "SPORT_MSQ_BACKUP_QUEUE";

    @Override
    public Long getMsgWaitTime() {
        return 60L;
    }

    @Override
    public String getQueueName() {
        return SPORT_MSQ_QUEUE;
    }

    @Override
    public String getBackupQueueName() {
        return SPORT_MSQ_BACKUP_QUEUE;
    }

    @SneakyThrows
    @Override
    public void doConsumeMsg(Object message) {
        if (message instanceof User) {
            User user = (User)message;
            if (user.getName().equals("张安8")){//模拟消费消息异常
                throw new Exception("消费消息失败");
            }
            log.info("SportMsgReceiver receive message = {}", JSON.toJSONString (user));
        }
    }
}
