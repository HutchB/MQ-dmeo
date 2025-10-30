package com.itheima.publisher;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest
@Slf4j
public class SpringAmqpTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage() {
        // 1.队列名
        String queueName = "simple.queue";
        // 2.消息
        String message = "hello,rabbitmq";
        // 3.发送消息
        rabbitTemplate.convertAndSend(queueName, message);
        System.out.println("发送消息成功：" + message);
    }

    @Test
    public void testWorkQueue() {
        // 1.队列名
        String queueName = "work.queue";
        // 3.发送消息
        for (int i = 0; i < 50; i++) {
            // 2.消息
            String message = "hello,rabbitmq——" + i;
            rabbitTemplate.convertAndSend(queueName, message);
        }
    }

    @Test
    public void testFanoutQueue() {
        // 1.交换机名
        String exchange = "hmall.fanout";
        // 2.消息
        String message = "hello,everyone";
        // 3.发送消息
        rabbitTemplate.convertAndSend(exchange, null, message);
    }

    @Test
    public void testDirectQueue() {
        // 1.交换机名
        String exchange = "hmall.direct";
        // 2.消息
        String message = "hello,everyone";
        // 3.发送消息
        rabbitTemplate.convertAndSend(exchange, "yellow", message);
    }


    @Test
    public void testTopicQueue() {
        // 1.交换机名
        String exchange = "hmall.topic";
        // 2.消息
        String message = "新闻";
        // 3.发送消息
        rabbitTemplate.convertAndSend(exchange, "news", message);
        //rabbitTemplate.convertAndSend(exchange, "china", message);
        //rabbitTemplate.convertAndSend(exchange, "china.news", message);
    }


    @Test
    public void testSendObject()    {
        HashMap<String, Object> message = new HashMap<>();
        message.put("name", "tom");
        message.put("age", 18);
        // 3.发送消息
        rabbitTemplate.convertAndSend("object.queue", message);
    }


    @Test
    void testPublisherConfirm() {
        // 1.创建CorrelationData
        CorrelationData cd = new CorrelationData();
        // 2.给Future添加ConfirmCallback
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                // 2.1.Future发生异常时的处理逻辑，基本不会触发
                log.error("send message fail", ex);
            }
            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                // 2.2.Future接收到回执的处理逻辑，参数中的result就是回执内容
                if(result.isAck()){ // result.isAck()，boolean类型，true代表ack回执，false 代表 nack回执
                    log.debug("发送消息成功，收到 ack!");
                }else{ // result.getReason()，String类型，返回nack时的异常描述
                    log.error("发送消息失败，收到 nack, reason : {}", result.getReason());
                }
            }
        });
        // 3.发送消息
        rabbitTemplate.convertAndSend("hmall.direct", "q", "hello", cd);
    }
}
