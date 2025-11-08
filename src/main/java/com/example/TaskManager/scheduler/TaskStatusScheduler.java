package com.example.TaskManager.scheduler;

import com.example.TaskManager.entities.Task;
import com.example.TaskManager.enums.Status;
import com.example.TaskManager.repository.TaskRepository;
import com.example.TaskManager.service.impltaskservice.ITaskService;
import com.example.TaskManager.service.taskservice.TaskService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
public class TaskStatusScheduler {

    TaskService taskService;
    TaskRepository taskRepository;

    public TaskStatusScheduler(TaskService taskService, TaskRepository taskRepository){
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @Scheduled(fixedRate = 10000)
    @Modifying
    @Transactional
    public void updateTaskStatus(){
        log.info("Scheduler Running for checking tasks to update status!!");
        List<Task> tasks = getTaskByStatus(Status.PENDING.toString());
        log.info("Found Pending Tasks {}: ",tasks.size());
        tasks.stream().filter(task->
                task.getCreated_at().isBefore(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
        ).forEach(task->{
                task.setStatus(Status.IN_PROGRESS);
                taskRepository.save(task);
        });
        log.info("Task status update process successful!!");
    }

    public List<Task> getTaskByStatus(String value){
        Status status = ITaskService.computeStatus(value);
        List<Task> tasks = taskRepository.findByStatus(status);
        return tasks;

    }

}
