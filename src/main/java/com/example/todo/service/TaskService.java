package com.example.todo.service;

import com.example.todo.entity.TaskEntity;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.repository.TaskRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskEventProducer producer;

    private final Counter taskCreatedCounter;
    private final Counter taskDeletedCounter;
    private final Timer taskCreateTimer;

    public TaskService(
            TaskRepository repository,
            TaskEventProducer producer,
            MeterRegistry registry
    ) {
        this.repository = repository;
        this.producer = producer;

        this.taskCreatedCounter = registry.counter("tasks.created");
        this.taskDeletedCounter = registry.counter("tasks.deleted");
        this.taskCreateTimer = registry.timer("tasks.create.duration");
    }

    @Cacheable("tasks-all")
    public List<TaskEntity> findAll() {
        log.info("Fetching all tasks");
        return repository.findAll();
    }

    @Cacheable(value = "tasks", key = "#id")
    public TaskEntity findById(Long id) {
        log.info("Fetching task id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Task not found id={}", id);
                    return new TaskNotFoundException("Task not found");
                });
    }

    @Caching(
            put = @CachePut(value = "tasks", key = "#result.id"),
            evict = @CacheEvict(value = "tasks-all", allEntries = true)
    )
    public TaskEntity create(TaskEntity task) {
        log.info("Creating task title={}", task.getTitle());

        TaskEntity saved = taskCreateTimer.record(() -> repository.save(task));

        taskCreatedCounter.increment();
        producer.send("TASK_CREATED id=" + saved.getId());

        log.info("Task created id={}", saved.getId());
        return saved;
    }

    @Caching(
            put = @CachePut(value = "tasks", key = "#id"),
            evict = @CacheEvict(value = "tasks-all", allEntries = true)
    )
    public TaskEntity update(Long id, TaskEntity data) {
        log.info("Updating task id={}", id);

        TaskEntity task = findById(id);
        task.setTitle(data.getTitle());
        task.setCompleted(data.isCompleted());

        TaskEntity saved = repository.save(task);
        producer.send("TASK_UPDATED id=" + saved.getId());

        log.info("Task updated id={}", saved.getId());
        return saved;
    }

    @CacheEvict(value = {"tasks", "tasks-all"}, allEntries = true)
    public void delete(Long id) {
        log.info("Deleting task id={}", id);

        repository.deleteById(id);
        taskDeletedCounter.increment();

        producer.send("TASK_DELETED id=" + id);

        log.info("Task deleted id={}", id);
    }
}
