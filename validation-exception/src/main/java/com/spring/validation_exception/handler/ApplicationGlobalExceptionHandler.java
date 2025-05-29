package com.spring.validation_exception.handler;

import com.spring.validation_exception.dto.ErrorsDTO;
import com.spring.validation_exception.dto.ServiceResponse;
import com.spring.validation_exception.exception.CourseServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApplicationGlobalExceptionHandler {

    //MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        ServiceResponse<?> serviceResponse = new ServiceResponse<>();
        List<ErrorsDTO> errorDTOList = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorsDTO errorDTO = new ErrorsDTO(error.getField()+" : "+error.getDefaultMessage());
                    errorDTOList.add(errorDTO);
                });
        serviceResponse.setStatus(HttpStatus.BAD_REQUEST);
        serviceResponse.setErrors(errorDTOList);

        return serviceResponse;
    }

    @ExceptionHandler(CourseServiceException.class)
    public ServiceResponse<?> handleServiceException(CourseServiceException exception){
        ServiceResponse<?> serviceResponse = new ServiceResponse<>();
        List<ErrorsDTO> errorDTOList = new ArrayList<>();
        errorDTOList.add(new ErrorsDTO(exception.getMessage()));
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setErrors(errorDTOList);
        return serviceResponse;
    }
}
