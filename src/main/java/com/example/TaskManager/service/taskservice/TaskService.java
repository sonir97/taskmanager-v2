package com.example.TaskManager.service.taskservice;

import com.example.TaskManager.dtos.TaskDto;
import com.example.TaskManager.dtos.createTask.CreateTaskDto;
import com.example.TaskManager.dtos.createTask.CreateTaskResponseDto;
import com.example.TaskManager.dtos.updateTask.UpdateTaskDto;
import com.example.TaskManager.dtos.updateTask.UpdateTaskResponseDto;
import com.example.TaskManager.entities.Task;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService {

    List<TaskDto> getAllTasks();
    CreateTaskResponseDto createTask(CreateTaskDto task);
    CreateTaskResponseDto getTaskById(String id);
    List<TaskDto> getTaskByStatus(String status);
    void deleteTaskWithId(String id);
    UpdateTaskResponseDto updateTask(String id, UpdateTaskDto task);
    UpdateTaskResponseDto updateStatus(String id, String status);
}
