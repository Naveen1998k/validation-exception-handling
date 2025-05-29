package com.spring.validation_exception.exception;

public class CourseServiceException extends RuntimeException {

   public CourseServiceException(String message){
        super(message);
    }

    public CourseServiceException() {
    }
}
