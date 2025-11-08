package com.example.TaskManager.dtos.updateTask;

public record UpdateTaskResponseDto(String id, String title, String description,
                                    String dueDate, String status) {
}
