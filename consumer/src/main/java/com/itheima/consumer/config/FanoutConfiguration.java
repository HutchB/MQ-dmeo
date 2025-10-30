package com.itheima.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfiguration {

    @Bean
    public FanoutExchange fanoutExchange(){
//        return new FanoutExchange("hmall.fanout", true, false);
        return ExchangeBuilder.fanoutExchange("hmall.fanout").durable(true).build();
    }

    @Bean
    public Queue fanoutQueue1(){
        return QueueBuilder.durable("fanout.queue1").build();
    }
    @Bean
    public Queue fanoutQueue2(){
        return QueueBuilder.durable("fanout.queue2").build();
    }

    @Bean
    public Binding fanoutQueue1Binding(FanoutExchange fanoutExchange, Queue fanoutQueue1){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }
    @Bean
    public Binding fanoutQueue2Binding(FanoutExchange fanoutExchange, Queue fanoutQueue2){
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }


}
