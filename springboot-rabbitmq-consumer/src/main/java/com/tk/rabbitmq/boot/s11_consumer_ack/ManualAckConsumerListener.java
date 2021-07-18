package com.tk.rabbitmq.boot.s11_consumer_ack;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * Consumer ACK机制
 * 1、设置消费端签收模式acknowledge为手动签收
 * 2、将监听器实现ChannelAwareMessageListener
 * 3、如果消息消费成功，调用Channel的basicAck()方法签收消息
 * 4、入股消息消费失败，调用Channel的basicNack()方法拒绝签收，让Broker重新发送消息给消费者
 *
 */
@Component
@Slf4j
public class ManualAckConsumerListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        try {
            // 1、接收转换消息
            log.info("message={}", JSON.toJSONString(message.getBody()));

            // 2、处理业务逻辑
            log.info("处理业务逻辑......");

            // 3、手动签收
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),  false);
        }catch (Exception ex){

            // 出现异常
            log.error("出现异常={}" ,ex);

            // 拒绝签收
            // 参数1 ：消息传递标识
            // 参数2：是否批量签收
            // 参数3：requeue 重回队列.  如果消息消费失败，消息是否重回队列 . true: 消息重回队列，broker会重新发送消息给消费者
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
