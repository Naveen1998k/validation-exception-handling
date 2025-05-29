package com.spring.validation_exception.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "course")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    private String name;
    private String trainerName;
    private String duration; // days

    private Date startDate; // Assuming startDate is a String for simplicity
    private String courseType; // Live OR Recording
    private double fees;
    private boolean certificateAvailable;
    private String description;
    private String emailId;
    private String contact;


}
