package com.example.todo.service;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.dto.UpdateTaskRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    @Test
    void findAll_shouldReturnTasks() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test");

        when(repository.findAll()).thenReturn(List.of(task));

        List<Task> result = service.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test");

        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnTask() {
        Task task = new Task();
        task.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(task));

        Task result = service.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void updatePartially_shouldUpdateOnlyProvidedFields() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Old title");

        UpdateTaskRequest dto = new UpdateTaskRequest();
        dto.setTitle("New title");

        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(repository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        Task updated = service.updatePartially(1L, dto);

        assertThat(updated.getTitle()).isEqualTo("New title");
    }

    @Test
    void delete_shouldCallRepository() {
        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
