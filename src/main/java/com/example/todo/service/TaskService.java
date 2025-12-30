package com.example.todo.service;

<<<<<<< HEAD
import com.example.todo.dto.TaskRequest;
import com.example.todo.dto.TaskResponse;
import com.example.todo.dto.TaskUpdateRequest;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
=======
import com.example.todo.dto.*;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.model.Task;
import com.example.todo.repository.InMemoryTaskRepository;
>>>>>>> 4c2f6dcdf75e617e9e2f4e8fdca3bce72deaba29
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

<<<<<<< HEAD
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
=======
    private InMemoryTaskRepository repository;

    public TaskService(InMemoryTaskRepository repository) {
>>>>>>> 4c2f6dcdf75e617e9e2f4e8fdca3bce72deaba29
        this.repository = repository;
    }

    public TaskResponse create(TaskRequest request) {
        Task task = new Task(
                null,
                request.getTitle(),
                request.getDescription(),
                request.getStatus()
        );
        return toResponse(repository.save(task));
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

<<<<<<< HEAD
    public TaskResponse replace(Long id, TaskRequest request) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());

        return toResponse(repository.save(task));
    }

=======
>>>>>>> 4c2f6dcdf75e617e9e2f4e8fdca3bce72deaba29
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

<<<<<<< HEAD
        return toResponse(repository.save(task));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
=======
        return toResponse(repository.update(task));
    }

    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        repository.delete(id);
>>>>>>> 4c2f6dcdf75e617e9e2f4e8fdca3bce72deaba29
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
