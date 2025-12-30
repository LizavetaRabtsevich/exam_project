package com.example.todo.controller;

<<<<<<< HEAD
import com.example.todo.dto.TaskRequest;
import com.example.todo.dto.TaskResponse;
import com.example.todo.dto.TaskUpdateRequest;
=======
import com.example.todo.dto.*;
>>>>>>> 4c2f6dcdf75e617e9e2f4e8fdca3bce72deaba29
import com.example.todo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

<<<<<<< HEAD
    private final TaskService service;
=======
    private TaskService service;
>>>>>>> 4c2f6dcdf75e617e9e2f4e8fdca3bce72deaba29

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<TaskResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody TaskRequest request) {
        return service.create(request);
    }

<<<<<<< HEAD
    @PutMapping("/{id}")
    public TaskResponse replace(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request
    ) {
        return service.replace(id, request);
    }

=======
>>>>>>> 4c2f6dcdf75e617e9e2f4e8fdca3bce72deaba29
    @PatchMapping("/{id}")
    public TaskResponse update(
            @PathVariable Long id,
            @RequestBody TaskUpdateRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
