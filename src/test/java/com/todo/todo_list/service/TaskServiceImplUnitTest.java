package com.todo.todo_list.service;

import com.todo.todo_list.entity.Comment;
import com.todo.todo_list.entity.Task;
import com.todo.todo_list.repository.CommentRepository;
import com.todo.todo_list.repository.TaskRepository;
import com.todo.todo_list.service.impl.TaskServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * @author by piyumi_navodani
 */
@ExtendWith(MockitoExtension.class)
public class TaskServiceImplUnitTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setTitle("New Task");

        Task savedTask = new Task();
        savedTask.setId(UUID.randomUUID());
        savedTask.setTitle("New Task");

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.createTask(task);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTask() {
        UUID id = UUID.randomUUID();
        Task existing = new Task();
        existing.setId(id);
        existing.setTitle("Old");

        Task updated = new Task();
        updated.setTitle("Updated");
        updated.setDescription("New desc");

        when(taskRepository.findById(id)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenReturn(existing);

        Task result = taskService.updateTask(id, updated);

        assertEquals("Updated", result.getTitle());
        verify(taskRepository).save(existing);
    }

    @Test
    void testToggleCompletion() {
        UUID id = UUID.randomUUID();
        Task task = new Task();
        task.setId(id);
        task.setCompleted(false);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.toggleCompletion(id, true);

        assertTrue(result.isCompleted());
    }

    @Test
    void testDeleteTask() {
        UUID id = UUID.randomUUID();

        doNothing().when(taskRepository).deleteById(id);

        taskService.deletTask(id);

        verify(taskRepository).deleteById(id);
    }

    @Test
    void testAddComment() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task();
        task.setId(taskId);
        task.setComments(new ArrayList<>());

        Comment comment = new Comment();
        comment.setText("Hello");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.addComment(taskId, comment);

        assertEquals(1, result.getComments().size());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void testGetTaskById_NotFound() {
        UUID id = UUID.randomUUID();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(id));
    }
}
