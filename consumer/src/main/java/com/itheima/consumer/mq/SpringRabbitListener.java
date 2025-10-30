package com.itheima.consumer.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueueMessage(String message) {
        System.out.println("监听到simple.queue的消息：" + message);

    }

    @RabbitListener(queues = "work.queue")
    public void listenWorkQueueMessag1e(String message) throws InterruptedException {
        System.out.println("消费者1接收到消息：" + message + "，时间：" + LocalTime.now());
        Thread.sleep(25);
    }

    @RabbitListener(queues = "work.queue")
    public void listenWorkQueueMessage2(String message) throws InterruptedException {
        System.err.println("消费者2接收到消息：" + message + "，时间：" + LocalTime.now());
        Thread.sleep(200);
    }

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String message) throws InterruptedException {
        System.err.println("消费者1：" + message + "，时间：" + LocalTime.now());
        Thread.sleep(200);
    }
    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String message) throws InterruptedException {
        System.err.println("消费者2：" + message + "，时间：" + LocalTime.now());
        Thread.sleep(200);
    }



}
