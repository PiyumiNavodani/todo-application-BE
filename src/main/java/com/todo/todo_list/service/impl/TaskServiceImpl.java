package com.todo.todo_list.service.impl;

import com.todo.todo_list.dto.CommonResponse;
import com.todo.todo_list.dto.TaskDto;
import com.todo.todo_list.dto.TaskRequestDto;
import com.todo.todo_list.dto.TaskUpdateDto;
import com.todo.todo_list.entity.Comment;
import com.todo.todo_list.entity.Task;
import com.todo.todo_list.repository.CommentRepository;
import com.todo.todo_list.repository.TaskRepository;
import com.todo.todo_list.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author by piyumi_navodani
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    @Override
    public Task createTask(Task task) {
        task.setId(null);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(UUID id, Task updated) {
        Task task = getTaskById(id);
        task.setTitle(updated.getTitle());
        task.setDescription(updated.getDescription());
        task.setDueDate(updated.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());
        task.setCompleted(updated.isCompleted());
        return taskRepository.save(task);
    }

    @Override
    public Task toggleCompletion(UUID id, boolean completed) {
        Task task = getTaskById(id);
        task.setCompleted(completed);
        return taskRepository.save(task);
    }

    @Override
    public void deletTask(UUID id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task getTaskById(UUID id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public List<Task> getTasks(String search, Boolean completed, LocalDate dueDate, String filterType) {
        return taskRepository.findAll();
    }

    @Override
    public Task addComment(UUID taskId, Comment comment) {
        Task task = getTaskById(taskId);
        comment.setId(null);
        comment.setText(comment.getText());
        comment.setTimeStamp(LocalDateTime.now());
        task.getComments().add(comment);
        commentRepository.save(comment);
        return taskRepository.save(task);
    }
}
