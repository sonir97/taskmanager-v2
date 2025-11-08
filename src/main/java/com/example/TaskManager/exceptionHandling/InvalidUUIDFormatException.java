package com.example.TaskManager.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUUIDFormatException extends RuntimeException{
    public InvalidUUIDFormatException(String resourceName, String fieldName, String fieldValue){
        super(String.format("%s Invalid %s Format : %s",resourceName,fieldName,fieldValue ));
    }
}
