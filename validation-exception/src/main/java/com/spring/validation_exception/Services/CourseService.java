package com.spring.validation_exception.Services;

import com.spring.validation_exception.Entity.CourseEntity;
import com.spring.validation_exception.Utils.AppUtils;
import com.spring.validation_exception.dao.CourseDao;
import com.spring.validation_exception.dto.CourseRequestDTO;
import com.spring.validation_exception.dto.CourseResponseDTO;
import com.spring.validation_exception.exception.CourseServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CourseService {


    private CourseDao courseDao;



    //create course object in DB -> POST
    public CourseResponseDTO onBoardCourse(CourseRequestDTO course){
       CourseEntity courseEntity= AppUtils.mapToEntity(course);
       CourseEntity entity=null;
       try {
           entity = courseDao.save(courseEntity);

           CourseResponseDTO courseResponseDTO = AppUtils.mapToResponse(entity);
           courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
           return courseResponseDTO;

       }catch (Exception e){
           throw new CourseServiceException("Onboarding course failed: " + e.getMessage());
       }



    }

    //load all the course from Database  // GET
    public List<CourseResponseDTO> findAllCourses(){

          Iterable<CourseEntity> courseEntities=null;
          try {
              courseEntities = courseDao.findAll();

              return StreamSupport.stream(courseEntities.spliterator(), false).map(AppUtils::mapToResponse)
                      .collect(Collectors.toList());
          } catch (Exception e) {
              throw new CourseServiceException("Viewing all courses failed: " + e.getMessage());
          }


    }

    //filter course by course id //GET
    public CourseResponseDTO findByCourseId(Integer courseId){
        CourseEntity courseEntity = courseDao.findById(courseId).orElseThrow(()->new RuntimeException(courseId+" Not valid"));
        return AppUtils.mapToResponse(courseEntity);
        // return courseList.stream().filter(course-> course.getCourseId()==courseId).findFirst().orElse( null);
    }

    //delete course  //DELETE
    public void deleteCourse(Integer courseId){
          courseDao.deleteById(courseId);
    }


    //update the course //PUT
    public CourseResponseDTO updateCourse(int courseId, CourseRequestDTO courseRequest) {
         CourseEntity existingCourseEntity= courseDao.findById(courseId).orElse(null);
        existingCourseEntity.setName(courseRequest.getName());
        existingCourseEntity.setTrainerName(courseRequest.getTrainerName());
        existingCourseEntity.setDuration(courseRequest.getDuration());
        existingCourseEntity.setStartDate(courseRequest.getStartDate());
        existingCourseEntity.setCourseType(courseRequest.getCourseType());
        existingCourseEntity.setFees(courseRequest.getFees());
        existingCourseEntity.setCertificateAvailable(courseRequest.isCertificateAvailable());
        existingCourseEntity.setDescription(courseRequest.getDescription());
            CourseEntity courseEntity=courseDao.save(existingCourseEntity);
        return AppUtils.mapToResponse(courseEntity);

    }

}
