package com.spring.validation_exception.Controller;


import com.spring.validation_exception.Services.CourseService;
import com.spring.validation_exception.dto.CourseRequestDTO;
import com.spring.validation_exception.dto.CourseResponseDTO;
import com.spring.validation_exception.dto.ServiceResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/add")
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody @Valid CourseRequestDTO course){
        ServiceResponse<CourseResponseDTO> serviceResponse=new ServiceResponse<>();

        try{
            CourseResponseDTO newCourse=  courseService.onBoardCourse(course);
            serviceResponse.setStatus(HttpStatus.OK);
            serviceResponse.setResponse(newCourse);
        } catch (Exception e) {
            serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return serviceResponse;
    }

    @GetMapping("/view")
    public ServiceResponse<List<CourseResponseDTO>> findAllCourses(){
        List<CourseResponseDTO> courseResponseDTOS= courseService.findAllCourses();
            return new ServiceResponse<>(HttpStatus.OK,courseResponseDTOS);
    }

    @GetMapping("/search/{courseId}")
    public ServiceResponse<CourseResponseDTO> findCourseById(@PathVariable int courseId){

        CourseResponseDTO courseResponseDTO= courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK,courseResponseDTO);
    }

    @GetMapping("/search/request")
    public ServiceResponse<CourseResponseDTO> findCourseByIdReq(@RequestParam(required = false,defaultValue = "1") int courseId){
        CourseResponseDTO courseResponseDTO= courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK,courseResponseDTO);
    }

    @DeleteMapping("/delete/{courseId}")
    public ServiceResponse<String> deleteCourse(@PathVariable int courseId){

        courseService.deleteCourse(courseId);
        return new ServiceResponse<>(HttpStatus.NO_CONTENT,"Course Deleted Successfully");
    }

    @PutMapping("/update/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId, @RequestBody CourseRequestDTO course){
             CourseResponseDTO courseResponseDTO= courseService.updateCourse(courseId,course);

        return new ServiceResponse<>(HttpStatus.OK,courseResponseDTO);
    }
}
