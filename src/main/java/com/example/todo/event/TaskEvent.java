package com.example.todo.event;

import java.io.Serializable;

public class TaskEvent implements Serializable {

    private Long taskId;
    private String type;

    public TaskEvent() {}

    public TaskEvent(Long taskId, String type) {
        this.taskId = taskId;
        this.type = type;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getType() {
        return type;
    }
}
