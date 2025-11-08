package com.example.TaskManager.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String id;


    @NotBlank(message = "Title should not be empty!!")
    @JsonProperty(required = true)
    private String title;

    @NotBlank(message = "Title should not be empty!!")
    @Size(min = 10, max=200, message = "Description should be atleast 10 characters long and atmost 200 characters!!")
    @JsonProperty(required = true)
    private String description;


    private String status;

    @Future(message = "Date must be in the future!!")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String dueDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime taskCreatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime taskUpdatedAt;

    public TaskDto(String id, String title, String description, String status, String dueDate){
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }


}
