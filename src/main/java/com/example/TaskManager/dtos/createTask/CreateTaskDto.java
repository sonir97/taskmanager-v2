package com.example.TaskManager.dtos.createTask;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateTaskDto {

    @NotBlank(message = "Title should not be empty!!")
    @JsonProperty(required = true)
    private String title;

    @NotBlank(message = "Title should not be empty!!")
    @Size(min = 10, max=200, message = "Description should be atleast 10 characters long and atmost 200 characters!!")
    @JsonProperty(required = true)
    private String description;

    @Future(message = "Date must be in the future!!")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
}
