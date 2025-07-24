package com.todo.todo_list.service;

import com.todo.todo_list.dto.CommonResponse;
import com.todo.todo_list.dto.TaskRequestDto;
import com.todo.todo_list.dto.TaskUpdateDto;
import com.todo.todo_list.entity.Comment;
import com.todo.todo_list.entity.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author by piyumi_navodani
 */
public interface TaskService {
    Task createTask(Task task);
    Task updateTask(UUID id, Task updated);
    Task toggleCompletion(UUID id, boolean completed);
    void deletTask(UUID id);
    Task getTaskById(UUID id);
    List<Task> getTasks(String search, Boolean completed, LocalDate dueDate, String filterType);
    Task addComment(UUID taskId, Comment comment);
}
