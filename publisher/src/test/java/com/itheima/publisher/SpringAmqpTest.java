package com.itheima.publisher;


import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
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
}
