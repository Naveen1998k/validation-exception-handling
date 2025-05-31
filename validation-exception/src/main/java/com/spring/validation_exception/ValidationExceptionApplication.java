package com.spring.validation_exception;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info =@Info(title = "Course Service API", description = "API for managing courses with validation exceptions" ))
@SpringBootApplication
public class ValidationExceptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidationExceptionApplication.class, args);
	}

}
