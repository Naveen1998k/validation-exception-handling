package com.spring.validation_exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.validation_exception.Annotation.CourseTypeValidator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDTO
{

    @NotBlank(message = "Course name should not be blank or Null")
    private String name;

    @NotEmpty(message = "Trainer name should not be empty")
    private String trainerName;
    @NotNull(message = "Duration should not be null")
    private String duration; // days

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    @FutureOrPresent(message = "Start date can not be before from today")
    private Date startDate;

    @CourseTypeValidator(message = "Invalid course type. It should be either Live or Recording.")
    private String courseType; //Live OR Recodring

    @Min(value = 1500, message = "Fees should be greater than 1500")
    @Max(value = 5000, message = "Fees should be less than 5000")
    private double fees;

    private boolean certificateAvailable;
    @NotEmpty(message = "Description should not be empty")
    @Length(min=10, max=100, message = "Description should be between 10 to 100 characters")
    private String description;
    @Email(message = "invalid email id")
    private String emailId;
    @Pattern(regexp = "^[0-9]{10}$")
    private String contact;
}
