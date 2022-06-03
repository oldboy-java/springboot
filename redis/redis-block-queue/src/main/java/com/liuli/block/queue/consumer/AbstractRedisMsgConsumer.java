package com.liuli.block.queue.consumer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 定义抽象的消息消费者，启动时监听队列，借助rightPop命令实现从Redis中队列中获取消息进行消费
 */
@Slf4j
public abstract class AbstractRedisMsgConsumer  extends  Thread implements RedisMsgConsumer  {

    /**
     *  初始化方法中开启一个线程监听队列中的消息
     */
    @PostConstruct
    public void init(){
        //开启线程
        this.start();
    }

    @Override
    public void run() {
        // 消费消息
        this.consume();
    }

    /**
     *  从Redis队列中获取消息等待超时时间，单位秒
     * @return
     */
    public abstract Long getMsgWaitTime();

    /**
     * 获取队列名称
     * @return
     */
    public  abstract  String  getQueueName();


    /**
     * 获取备份队列名称
     * @return
     */
    public  abstract  String  getBackupQueueName();


    /**
     * 真正执行消费消息
     * @param message
     */
    public abstract void  doConsumeMsg(Object message);


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 消费消息
     */
    @Override
    public void consume() {
        while (true) {
            //rightPop会将使当前连接阻塞，直至从队列获取到消息或者等待超时时间后释放，为了一直监听队列消息，需要
            //使用死循环

            Object msg = null;
            // 如果未指定备份队列，则使用rightPop消费消息，不进行备份处理
            if (StringUtils.isBlank(getBackupQueueName())) {
                 msg = redisTemplate.opsForList().rightPop(getQueueName(), getMsgWaitTime(), TimeUnit.SECONDS);
            }else {
                // 使用rightPopAndLeftPush消费消息，同时将消息备份到备份队列中
                msg = redisTemplate.opsForList().rightPopAndLeftPush(getQueueName(), getBackupQueueName(), getMsgWaitTime(), TimeUnit.SECONDS);
            }

            // 获取到消息执行消息处理
            if (msg != null) {
                log.info("从Redis队列中获取到消息={}，开始进行消费处理", JSON.toJSONString(msg));
                try{
                    doConsumeMsg(msg);
                    //消费成功，删除备份队列中的指定消息
                    redisTemplate.opsForList().remove(getBackupQueueName(), 1, msg);
                }catch (Exception ex) {
                    log.error("消费消息失败={}，消息内容={}", ex, JSON.toJSONString(msg));
                }
            }else {
                log.info("从Redis队列中未获取到消息，继续监听队列获取消息，等待时间={}秒",getMsgWaitTime());
            }
        }
    }
}
