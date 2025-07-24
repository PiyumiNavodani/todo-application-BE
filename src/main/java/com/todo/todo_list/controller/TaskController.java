package com.todo.todo_list.controller;

import com.todo.todo_list.dto.CommonResponse;
import com.todo.todo_list.dto.TaskRequestDto;
import com.todo.todo_list.dto.TaskUpdateDto;
import com.todo.todo_list.entity.Comment;
import com.todo.todo_list.entity.Task;
import com.todo.todo_list.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author by piyumi_navodani
 */

@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    /**
     * This is the endpoint for create new to-do task
     * @param taskRequestDto
     * @return ResponseEntity
     */
    @PostMapping
    public Task createTask(@RequestBody final Task task){
        log.info("TaskController.createTask() started...");
        return taskService.createTask(task);
    }

    /**
     * This is the endpoint to get most recent 5 to-do tasks
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable final UUID id, @RequestBody Task task){
        log.info("TaskController.getTasksList() started...");
        return taskService.updateTask(id, task);
    }

    /**
     * This is the endpoint to update the task status when it is done
     * @param taskUpdateDto
     * @return ResponseEntity
     */
    @PatchMapping("/{id}")
    public Task toggleComplete(@PathVariable final UUID id, @RequestBody Task task){
        log.info("TaskController.updateTaskStatus() started...");
        return taskService.toggleCompletion(id, task.isCompleted());
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) String search,
                               @RequestParam(required = false) Boolean completed,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dueDate,
                               @RequestParam(required = false) String filterType){
        log.info("TaskController.updateTaskStatus() started...");
        return taskService.getTasks(search, completed, dueDate, filterType);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable UUID id){
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable UUID id){
        taskService.deletTask(id);
    }

    @PostMapping("/{id}/comments")
    public Task addComment(@PathVariable UUID id, @RequestBody Comment comment){
        return taskService.addComment(id, comment);
    }
}
