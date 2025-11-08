package com.example.TaskManager.dtos.updateTask;

import com.example.TaskManager.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskDto {

    @Size(min = 10, max=50, message = "Title should be atleast 10 characters long and atmost 50 characters!!")
    @Nullable
    private String title;


    @Size(min = 10, max=200, message = "Description should be atleast 10 characters long and atmost 200 characters!!")
    @Nullable
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Status status;

    @Future(message = "Date must be in the future!!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Nullable
    private LocalDate dueDate;
}
