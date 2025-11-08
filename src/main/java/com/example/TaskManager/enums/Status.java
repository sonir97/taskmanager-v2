package com.example.TaskManager.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {

    PENDING,

    IN_PROGRESS,

    COMPLETE;

//    @JsonCreator
//    public static Status fromJson(String value){
//        for(Status status: values()){
//            if(status.name().equalsIgnoreCase(value)){
//                return status;
//            }
//        }
//
//        throw new IllegalArgumentException("Invalid Task Status: "+ value);
//    }




}
