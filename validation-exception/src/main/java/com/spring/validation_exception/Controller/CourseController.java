package com.spring.validation_exception.Controller;


import com.spring.validation_exception.Services.CourseService;
import com.spring.validation_exception.Utils.AppUtils;
import com.spring.validation_exception.dto.CourseRequestDTO;
import com.spring.validation_exception.dto.CourseResponseDTO;
import com.spring.validation_exception.dto.ServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "add a new course to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "course added successfully",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = CourseResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400",description = "validation error")
    })
    @PostMapping("/add")
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody @Valid CourseRequestDTO course){
        log.info("CourseController ::addCourse Method Started with request payload {}", AppUtils.convertToJson(course));
        ServiceResponse<CourseResponseDTO> serviceResponse=new ServiceResponse<>();

        try{

            CourseResponseDTO newCourse=  courseService.onBoardCourse(course);
            log.debug("CourseController ::addCourse Method Response : {}", AppUtils.convertToJson(newCourse));
            serviceResponse.setStatus(HttpStatus.OK);
            serviceResponse.setResponse(newCourse);

        } catch (Exception e) {
            log.error("CourseController ::addCourse Method Exception Occurred: while sending payload {}", AppUtils.convertToJson(course));
            serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("CourseController ::addCourse Method Completed with response {}", AppUtils.convertToJson(serviceResponse));
        return serviceResponse;
    }

    @Operation(summary = "fetch all courses from the system")
    @GetMapping("/view")
    public ServiceResponse<List<CourseResponseDTO>> findAllCourses(){
        log.info("CourseController::findAllCourse Method Execution Started");
        // Fetching all courses from the service
        List<CourseResponseDTO> courseResponseDTOS= courseService.findAllCourses();
        log.debug("Fetching all courses from database");
        return new ServiceResponse<>(HttpStatus.OK,courseResponseDTOS);
    }

    @Operation(summary = "fetch course by id from the system")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",description = "course fetched successfully",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = CourseResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404",description = "course not found")
    })
    @GetMapping("/search/{courseId}")
    public ServiceResponse<CourseResponseDTO> findCourseById(@PathVariable int courseId){
        log.info("CourseController::findCourseById method started with id {}",courseId);
        // Fetching course by ID from the service
        CourseResponseDTO courseResponseDTO= courseService.findByCourseId(courseId);
        log.debug("CourseController::findByCoursebyid {}", AppUtils.convertToJson(courseResponseDTO));
        log.info("CourseController::findCourseById method completed with response {}", AppUtils.convertToJson(courseResponseDTO));
        return new ServiceResponse<>(HttpStatus.OK,courseResponseDTO);
    }

    @Operation(summary = "fetch course by id from the system using request param")
    @GetMapping("/search/request")
    public ServiceResponse<CourseResponseDTO> findCourseByIdReq(@RequestParam(required = false,defaultValue = "1") int courseId){
        CourseResponseDTO courseResponseDTO= courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK,courseResponseDTO);
    }

    @Operation(summary = "delete course by id from the system")
    @DeleteMapping("/delete/{courseId}")
    public ServiceResponse<String> deleteCourse(@PathVariable int courseId){
        log.info("CourseController::deleteCourse method started with id {}",courseId);
        // Deleting course by ID from the service
        courseService.deleteCourse(courseId);
        log.info("CourseController::deleteCourse method completed for courseId {}",courseId);
        return new ServiceResponse<>(HttpStatus.NO_CONTENT,"Course Deleted Successfully");
    }

    @Operation(summary = "update course by id in the system")
    @PutMapping("/update/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId, @RequestBody CourseRequestDTO course){
        log.info("CourseController::updateCourse method started with id {} and request payload {}", courseId, AppUtils.convertToJson(course));
             CourseResponseDTO courseResponseDTO= courseService.updateCourse(courseId,course);
             log.debug("CourseController::updateCourse method response {}", AppUtils.convertToJson(courseResponseDTO));
             log.info("CourseController::updateCourse method completed for courseId {}", courseId);

        return new ServiceResponse<>(HttpStatus.OK,courseResponseDTO);
    }

    @Operation(summary = "log various levels of messages")
    @GetMapping("/log")
    public String loggingLevel() {
        log.trace("trace message");
        log.debug("debug message");
        log.info("info message");
        log.warn("warn message");
        log.error("error message");
        return "log printed in console";
    }
}
