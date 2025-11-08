package com.example.TaskManager.repository;

import com.example.TaskManager.entities.Task;
import com.example.TaskManager.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByStatus(Status status);
}
