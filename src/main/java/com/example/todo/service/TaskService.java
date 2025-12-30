package com.example.todo.service;

import com.example.todo.dto.TaskRequest;
import com.example.todo.dto.TaskResponse;
import com.example.todo.dto.TaskUpdateRequest;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.model.Task;
import com.example.todo.repository.InMemoryTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final InMemoryTaskRepository repository;

    public TaskService(InMemoryTaskRepository repository) {
        this.repository = repository;
    }

    public TaskResponse create(TaskRequest request) {
        Task task = new Task(
                null,
                request.getTitle(),
                request.getDescription(),
                request.getStatus()
        );

        Task savedTask = repository.save(task);
        return toResponse(savedTask);
    }

    public List<TaskResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse findById(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return toResponse(task);
    }

    public TaskResponse replace(Long id, TaskRequest request) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());

        Task updatedTask = repository.update(task);
        return toResponse(updatedTask);
    }

    public TaskResponse update(Long id, TaskUpdateRequest request) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        Task updatedTask = repository.update(task);
        return toResponse(updatedTask);
    }

    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        repository.delete(id);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }
}
