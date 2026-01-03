package com.example.todo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE = "task.queue";
    public static final String EXCHANGE = "task.exchange";
    public static final String ROUTING_KEY = "task.event";

    @Bean
    public Queue taskQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue taskQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(taskQueue)
                .to(exchange)
                .with(ROUTING_KEY);
    }
}
