package com.example.todo.repository;

import com.example.todo.model.Task;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTaskRepository {

    private Map<Long, Task> storage = new HashMap<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    public Task save(Task task) {
        Long id = idGenerator.getAndIncrement();
        task.setId(id);
        storage.put(id, task);
        return task;
    }

    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Task update(Task task) {
        storage.put(task.getId(), task);
        return task;
    }

    public void delete(Long id) {
        storage.remove(id);
    }
}
