//package com.todo.todo_list.service;
//
//import com.todo.todo_list.dto.CommonResponse;
//import com.todo.todo_list.dto.TaskDto;
//import com.todo.todo_list.dto.TaskRequestDto;
//import com.todo.todo_list.dto.TaskUpdateDto;
//import com.todo.todo_list.entity.Task;
//import com.todo.todo_list.repository.TaskRepository;
//import com.todo.todo_list.service.impl.TaskServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
///**
// * @author by piyumi_navodani
// */
//
//@ExtendWith(MockitoExtension.class)
//public class TaskServiceImplTest {
//
//    @Mock
//    private TaskRepository taskRepository;
//
//    @InjectMocks
//    private TaskServiceImpl taskService;
//
//    @Test
//    void testCreateTask_Success(){
//        TaskRequestDto taskRequestDto = new TaskRequestDto("Task1", "Description1");
//
//        CommonResponse commonResponse =  taskService.createTask(taskRequestDto);
//
//        assertEquals(HttpStatus.CREATED, commonResponse.getStatus());
//        assertEquals("New task created successfullly", commonResponse.getMessage());
//        assertNotNull(commonResponse.getData());
//    }
//
//    @Test
//    void testCreateTask_NullRequest(){
//        CommonResponse commonResponse = taskService.createTask(null);
//
//        assertEquals(HttpStatus.BAD_REQUEST, commonResponse.getStatus());
//        assertEquals("Request payload is empty", commonResponse.getMessage());
//        assertNull(commonResponse.getData());
//    }
//
//    @Test
//    void testGetTasksList_Success(){
//        Task task1 =    new Task();
//
//        task1.setTaskId(1);
//        task1.setTaskName("Task 1");
//        task1.setTaskDescription("Description 1");
//        task1.setTaskCompleted(false);
//        task1.setCreatedDate(LocalDateTime.now());
//
//        Task task2 = new Task();
//
//        task2.setTaskId(2);
//        task2.setTaskName("Task 2");
//        task2.setTaskDescription("Description 2");
//        task2.setTaskCompleted(false);
//        task2.setCreatedDate(LocalDateTime.now());
//
//        List<Task> taskList = Arrays.asList(task1, task2);
//
//        when(taskRepository.findTop5ByTaskCompletedFalseOrderByCreatedDateDesc()).thenReturn(taskList);
//
//        CommonResponse response = taskService.getTasksList();
//
//        assertEquals(HttpStatus.OK, response.getStatus());
//        assertEquals("Tasks list found and returned successfully", response.getMessage());
//        assertNotNull(response.getData());
//
//        List<TaskDto> returnedTaskList = (List<TaskDto>) response.getData();
//        assertEquals(2, returnedTaskList.size());
//        assertEquals("Task 1", returnedTaskList.get(0).getTaskName());
//    }
//
//    @Test
//    void testGetTasksList_NoTasks(){
//        when(taskRepository.findTop5ByTaskCompletedFalseOrderByCreatedDateDesc()).thenReturn(Collections.emptyList());
//
//        CommonResponse commonResponse = taskService.getTasksList();
//
//        assertEquals(HttpStatus.NOT_FOUND, commonResponse.getStatus());
//        assertEquals("There are no tasks to return", commonResponse.getMessage());
//        assertNull(commonResponse.getData());
//    }
//
//    @Test
//    void testUpdateTaskStatus_Success(){
//        Task task = new Task();
//        task.setTaskId(1);
//        task.setTaskCompleted(false);
//
//        TaskUpdateDto taskUpdateDto = new TaskUpdateDto(1, true);
//
//        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
//
//        CommonResponse commonResponse = taskService.updateTaskStatus(taskUpdateDto);
//
//        assertEquals(HttpStatus.OK, commonResponse.getStatus());
//        assertEquals("Task status updated successfully", commonResponse.getMessage());
//        assertNotNull(commonResponse.getData());
//    }
//
//    @Test
//    void testUpdateTaskStatus_NoTaskId(){
//        TaskUpdateDto taskUpdateDto = new TaskUpdateDto(null, true);
//
//        CommonResponse commonResponse = taskService.updateTaskStatus(taskUpdateDto);
//
//        assertEquals(HttpStatus.BAD_REQUEST, commonResponse.getStatus());
//        assertEquals("Task id should pass in order to update the record", commonResponse.getMessage());
//        assertNull(commonResponse.getData());
//    }
//}
