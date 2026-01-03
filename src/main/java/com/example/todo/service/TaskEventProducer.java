package com.example.todo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public TaskEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message) {
        try {
            rabbitTemplate.convertAndSend(
                    "task.exchange",
                    "task.event",
                    message
            );
            log.debug("RabbitMQ event sent: {}", message);
        } catch (Exception e) {
            log.warn("RabbitMQ unavailable, skipping event: {}", message);
        }
    }
}
