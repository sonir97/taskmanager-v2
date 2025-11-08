package com.example.TaskManager.controller;

import com.example.TaskManager.dtos.TaskDto;
import com.example.TaskManager.dtos.createTask.CreateTaskDto;
import com.example.TaskManager.dtos.createTask.CreateTaskResponseDto;
import com.example.TaskManager.dtos.updateTask.UpdateTaskDto;
import com.example.TaskManager.dtos.updateTask.UpdateTaskResponseDto;
import com.example.TaskManager.service.taskservice.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllTasks(){
        List<TaskDto> tasks = taskService.getAllTasks();
        if(tasks.isEmpty()){
            Map<String,String> response = Map.of("message", "No tasks found, please create few tasks");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getTaskById(@PathVariable String id){
        CreateTaskResponseDto task = taskService.getTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getTaskByStatus(@PathVariable String status){
        List<TaskDto> tasks = taskService.getTaskByStatus(status);
        if(tasks.isEmpty()){
            Map<String,String> response = Map.of("message", String.format("No tasks found with status %s",status));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(tasks);

    }


    @PostMapping
    public ResponseEntity<Object> createTask(@Valid @RequestBody CreateTaskDto createTaskDto){
        CreateTaskResponseDto createdTask = taskService.createTask(createTaskDto);

        return ResponseEntity.status(HttpStatus.OK).body(createdTask);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable String id){
        taskService.deleteTaskWithId(id);
        Map<String,String> message = Map.of("message","task successfully completed with id: "+ id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateTaskResponseDto> updateTask(@PathVariable String id,@Valid @RequestBody UpdateTaskDto updateTaskDto){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(id,updateTaskDto));
    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<UpdateTaskResponseDto> updateStatus(@PathVariable String id, @PathVariable String status){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateStatus(id,status));
    }



}
