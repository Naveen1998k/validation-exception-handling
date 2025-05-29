package com.spring.validation_exception.Annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public  class CourseTypeValidatorImpl implements ConstraintValidator<CourseTypeValidator,String> {


    @Override
    public boolean isValid(String courseType, ConstraintValidatorContext context) {
        List list= Arrays.asList("LIVE","RECORDING");
        return list.contains(courseType);

    }
}
