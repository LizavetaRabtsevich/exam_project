package com.example.todo.controller;

import com.example.todo.entity.TaskEntity;
import com.example.todo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<TaskEntity> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TaskEntity getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskEntity create(@RequestBody TaskEntity task) {
        return service.create(task);
    }

    @PutMapping("/{id}")
    public TaskEntity update(
            @PathVariable Long id,
            @RequestBody TaskEntity task
    ) {
        return service.update(id, task);
    }

    @PatchMapping("/{id}")
    public TaskEntity patch(
            @PathVariable Long id,
            @RequestBody TaskEntity task
    ) {
        return service.update(id, task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
