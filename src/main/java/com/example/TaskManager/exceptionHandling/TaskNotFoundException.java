package com.example.TaskManager.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(String resourceName, String fieldName, String fieldValue){
        super(String.format("%s not found for %s with value %s!!", resourceName,fieldName,fieldValue));
    }
}
