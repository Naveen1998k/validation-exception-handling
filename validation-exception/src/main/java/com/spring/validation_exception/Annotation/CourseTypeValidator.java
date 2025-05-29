package com.spring.validation_exception.Annotation;

import jakarta.validation.Constraint;


import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CourseTypeValidatorImpl.class)
public @interface CourseTypeValidator  {
    String message() default "Invalid course type. It should be either 'Live' or 'Recording'.";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};

}
