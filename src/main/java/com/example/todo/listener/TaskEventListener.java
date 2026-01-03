package com.example.todo.listener;

import com.example.todo.config.RabbitConfig;
import com.example.todo.event.TaskEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TaskEventListener {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handle(TaskEvent event) {
        System.out.println(
                "📩 EVENT RECEIVED: " +
                        event.getType() +
                        " | taskId=" +
                        event.getTaskId()
        );
    }
}
