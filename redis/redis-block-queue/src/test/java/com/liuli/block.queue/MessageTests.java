package com.liuli.block.queue;

import com.liuli.block.queue.consumer.SportMsgConsumer;
import com.liuli.block.queue.model.Weather;
import com.liuli.block.queue.producer.RedisMqMessage;
import com.liuli.block.queue.producer.RedisMsgProducer;
import com.liuli.publish.subscribe.model.User;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(classes =RedisBlockQueueApplication.class)
public class MessageTests {
    @Autowired
    private RedisMsgProducer producer;

    @Autowired
    private SportMsgConsumer sportMsgConsumer;


    /**
     * 发送用户对象消息
     */
    @Test
    @SneakyThrows
    public void sendUserMessage(){
        for(int i = 0;i < 10; i++) {
            User user = User.builder().name("张安" + i).age(100 + i).build();
            RedisMqMessage message = RedisMqMessage.builder().data(user).queue("SPORT_QUEUE").build();
            producer.send( message);
        }
        TimeUnit.MINUTES.sleep(1);
    }

    /**
     * 发送天气对象消息
     */
    @Test
    @SneakyThrows
    public void sendWeatherMessage(){
        for(int i = 0;i < 10; i++) {
            Weather weather = Weather.builder().name("晴" + i).build();
            RedisMqMessage message = RedisMqMessage.builder().data(weather).queue("WEATHER_QUEUE").build();
            producer.send( message);
        }
        TimeUnit.MINUTES.sleep(1);
    }


//    /**
//     * 多线程模拟多个消费者，消费队列中消息，一般不用主动去消费
//     */
//    @Test
//    @SneakyThrows
//    public void consumeMessage(){
//        CountDownLatch latch = new CountDownLatch(2);
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        for (int i =0 ;i < 2; i++) {
//            executorService.execute(() ->{
//                sportMsgConsumer.consume();
//                latch.countDown();
//            });
//        }
//        latch.await();
//    }

}
