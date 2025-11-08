package com.example.TaskManager.service.impltaskservice;

import com.example.TaskManager.dtos.TaskDto;
import com.example.TaskManager.dtos.createTask.CreateTaskDto;
import com.example.TaskManager.dtos.createTask.CreateTaskResponseDto;
import com.example.TaskManager.dtos.updateTask.UpdateTaskDto;
import com.example.TaskManager.dtos.updateTask.UpdateTaskResponseDto;
import com.example.TaskManager.entities.Task;
import com.example.TaskManager.enums.Status;
import com.example.TaskManager.exceptionHandling.TaskNotFoundException;
import com.example.TaskManager.repository.TaskRepository;
import com.example.TaskManager.service.taskservice.TaskService;
import com.example.TaskManager.utility.UUIDConvertor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ITaskService implements TaskService{

    TaskRepository taskRepository;

    public ITaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Modifying
    @Transactional
    @Override
    public CreateTaskResponseDto createTask(CreateTaskDto task) {

        LocalDate dueDate = task.getDueDate()!=null?task.getDueDate(): LocalDate.now().plusDays(7);
        Task newTask = convertToEntity(task);
        newTask.setDueDate(dueDate);
        Task savedTask = taskRepository.save(newTask);
        return convertToTaskResponse(savedTask);
    }

    @Override
    public CreateTaskResponseDto getTaskById(String id) {
        UUID uuid = UUIDConvertor.StringToUUIDConvertor(id);
        Optional<Task> task = taskRepository.findById(uuid);
        if(!task.isPresent()){
            throw new TaskNotFoundException("Task","Id",id);
        }
        return convertToTaskResponse(task.get());


    }

    @Override
    public List<TaskDto> getTaskByStatus(String value){
        Status status = computeStatus(value);
        List<Task> tasks = taskRepository.findByStatus(status);
        return tasks.stream().map(this::convertToDto).collect(Collectors.toList());

    }

    @Modifying
    @Transactional
    @Override
    public void deleteTaskWithId(String id) {
        UUID uuid = UUIDConvertor.StringToUUIDConvertor(id);
        Optional<Task> task = taskRepository.findById(uuid);

        if(!task.isPresent()){
            throw new TaskNotFoundException("task", "id", id);
        }

        taskRepository.deleteById(uuid);
    }

    @Modifying
    @Transactional
    @Override
    public UpdateTaskResponseDto updateTask(String id, UpdateTaskDto updateTask) {
        UUID uuid = UUIDConvertor.StringToUUIDConvertor(id);
        return taskRepository.findById(uuid).map(existingTask->{
                    updateTaskFromTaskDto(existingTask,updateTask);
                    Task updatedTask = taskRepository.save(existingTask);
                    return convertToUpdateResponseDto(updatedTask);
                }
                ).
                orElseThrow(()->{
                    throw new TaskNotFoundException("task","id",id);
                });

    }

    @Override
    public UpdateTaskResponseDto updateStatus(String id, String status) {
        UUID uuid = UUIDConvertor.StringToUUIDConvertor(id);
        return taskRepository.findById(uuid).map(existingTask->{
            Status s = computeStatus(status);
            existingTask.setStatus(s);
            Task updatedTask = taskRepository.save(existingTask);
            return convertToUpdateResponseDto(updatedTask);
        }).orElseThrow(()->{throw new TaskNotFoundException("Task","id",id);});
    }

    public Task updateTaskFromTaskDto(Task existingTask, UpdateTaskDto updateTask){
        if(updateTask.getTitle()!=null && !updateTask.getTitle().isBlank()){
            existingTask.setTitle(updateTask.getTitle());
        }
        if(updateTask.getDescription()!=null && !updateTask.getDescription().isBlank()){
            existingTask.setDescription(updateTask.getDescription());
        }
        if(updateTask.getStatus()!=null){
            existingTask.setStatus(updateTask.getStatus());
        }
        if(updateTask.getDueDate()!=null){
            existingTask.setDueDate(updateTask.getDueDate());
        }

        return existingTask;
    }

    public UpdateTaskResponseDto convertToUpdateResponseDto(Task task){
        return new UpdateTaskResponseDto(task.getId().toString(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate().toString(),
                task.getStatus().toString());
    }
    public static Task convertToEntity(CreateTaskDto newTask){
        Task task = new Task();
        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription());
        return task;
    }

    public CreateTaskResponseDto convertToTaskResponse(Task task){
        return new CreateTaskResponseDto(task.getId().toString(),task.getTitle().toString(),task.getDescription().toString(),
                task.getDueDate().toString(),task.getStatus().toString());
    }

    public TaskDto convertToDto(Task task){
        TaskDto taskDto = new TaskDto(task.getId().toString(),
                task.getTitle().toString(),
                task.getDescription().toString(),
                task.getStatus().toString(),
                task.getDueDate().toString(),
                task.getCreated_at(),
                task.getCreated_at());
        return taskDto;
    }

    public static Status computeStatus(String value){

        for(Status status: Status.values()){
            if(status.name().toString().equalsIgnoreCase(value)){
                return status;
            }
        }

        throw new IllegalArgumentException("No Status exists with value: "+ value);

    }



}
