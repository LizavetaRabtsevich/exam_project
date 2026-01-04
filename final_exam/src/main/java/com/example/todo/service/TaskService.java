package com.example.todo.service;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import com.example.todo.dto.UpdateTaskRequest;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Retry(name = "taskService")
    @CircuitBreaker(name = "taskService", fallbackMethod = "findAllFallback")
    @Cacheable("tasks")
    public List<Task> findAll() {
        System.out.println(">>> DATABASE CALL");
        return repository.findAll();
    }

    public Task findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public Task save(Task task) {
        return repository.save(task);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public Task updatePartially(Long id, UpdateTaskRequest dto) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (dto.getTitle() != null) {
            task.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }

        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }

        return repository.save(task);
    }

    public List<Task> findAllFallback(Throwable ex) {
        System.out.println("!!! FALLBACK: database unavailable");
        return List.of();
    }

}