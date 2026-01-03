package com.example.todo.service;

import com.example.todo.entity.TaskEntity;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.repository.TaskRepository;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Cacheable("tasks-all")
    public List<TaskEntity> findAll() {
        System.out.println("DB CALL: findAll()");
        return repository.findAll();
    }

    @Cacheable(value = "tasks", key = "#id")
    public TaskEntity findById(Long id) {
        System.out.println("DB CALL: findById " + id);
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    @Caching(
            put = @CachePut(value = "tasks", key = "#result.id"),
            evict = @CacheEvict(value = "tasks-all", allEntries = true)
    )
    public TaskEntity create(TaskEntity task) {
        return repository.save(task);
    }

    @Caching(
            put = @CachePut(value = "tasks", key = "#id"),
            evict = @CacheEvict(value = "tasks-all", allEntries = true)
    )
    public TaskEntity update(Long id, TaskEntity data) {
        TaskEntity task = findById(id);
        task.setTitle(data.getTitle());
        task.setCompleted(data.isCompleted());
        return repository.save(task);
    }

    @CacheEvict(value = {"tasks", "tasks-all"}, allEntries = true)
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
