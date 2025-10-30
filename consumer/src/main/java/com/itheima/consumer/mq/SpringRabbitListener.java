package com.itheima.consumer.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
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

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1",durable = "true"),
            exchange = @Exchange(name = "hmall.direct",type = ExchangeTypes.DIRECT),
            key = {"red","blue"}))
    public void listenDirectQueue1(String message) throws InterruptedException {
        System.err.println("消费者1监听到direct.queue1消息 ：" + message + "，时间：" + LocalTime.now());
        Thread.sleep(200);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2",durable = "true"),
            exchange = @Exchange(name = "hmall.direct",type = ExchangeTypes.DIRECT),
            key = {"red","yellow"}
    ))
    @RabbitListener(queues = "direct.queue2")
    public void listenDirectQueue2(String message) throws InterruptedException {
        System.err.println("消费者2监听到direct.queue2消息" + message + "，时间：" + LocalTime.now());
        Thread.sleep(200);
    }

    @RabbitListener(queues = "topic.queue1")
    public void listenTopicQueue1(String message) throws InterruptedException {
        System.err.println("消费者1监听到topic.queue1消息 ：" + message + "，时间：" + LocalTime.now());
        Thread.sleep(200);
    }

    @RabbitListener(queues = "topic.queue2")
    public void listenTopicQueue2(String message) throws InterruptedException {
        System.err.println("消费者2监听到topic.queue2消息" + message + "，时间：" + LocalTime.now());
        Thread.sleep(200);
    }


}
