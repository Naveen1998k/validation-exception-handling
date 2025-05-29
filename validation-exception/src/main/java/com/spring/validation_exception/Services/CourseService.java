package com.spring.validation_exception.Services;

import com.spring.validation_exception.Entity.CourseEntity;
import com.spring.validation_exception.Utils.AppUtils;
import com.spring.validation_exception.dao.CourseDao;
import com.spring.validation_exception.dto.CourseRequestDTO;
import com.spring.validation_exception.dto.CourseResponseDTO;
import com.spring.validation_exception.exception.CourseServiceException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseService {


    Logger log = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseDao courseDao;



    //create course object in DB -> POST
    public CourseResponseDTO onBoardCourse(CourseRequestDTO course){
       CourseEntity courseEntity= AppUtils.mapToEntity(course);
       CourseEntity entity=null;
       log.info("CourseService::OnboardingCourse method Execution Started");
       try {
           entity = courseDao.save(courseEntity);
           log.debug("Course Entity Response  from database : {} " , AppUtils.convertToJson(entity));
           CourseResponseDTO courseResponseDTO = AppUtils.mapToResponse(entity);
           courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
              log.debug("Course Response DTO :  {}" , AppUtils.convertToJson(courseResponseDTO));
           log.info("CourseService::OnboardingCourse method Execution Completed::  ");
           return courseResponseDTO;

       }catch (Exception e){
           log.error("CourseService::OnboardingCourse exception Occurs while persisting the course Object  " + e.getMessage());
           throw new CourseServiceException("Onboarding course failed: " + e.getMessage());

       }



    }

    //load all the course from Database  // GET
    public List<CourseResponseDTO> findAllCourses(){

          Iterable<CourseEntity> courseEntities=null;
          log.info("CourseService::findAllCourses method Execution Started");
          try {
              courseEntities = courseDao.findAll();
              log.debug("Course Entities Response from database : {} ", AppUtils.convertToJson(courseEntities));

              log.info("CourseService::findAllCourses method Execution Ended");
              return StreamSupport.stream(courseEntities.spliterator(), false).map(AppUtils::mapToResponse)
                      .collect(Collectors.toList());

          } catch (Exception e) {
              log.error("CourseService::findAllCourses exception Occurs while fetching all the course Objects  " + e.getMessage());
              throw new CourseServiceException("Viewing all courses failed: " + e.getMessage());
          }


    }

    //filter course by course id //GET
    public CourseResponseDTO findByCourseId(Integer courseId){
        log.info("CourseService::findByCourseId method Execution Started for courseId: {}", courseId);
        CourseEntity courseEntity = courseDao.findById(courseId).orElseThrow(()->new CourseServiceException(courseId+" Does Not Exist in System"));
       log.debug("Course Entity Response from database for courseId {} : {} ", courseId, AppUtils.convertToJson(courseEntity));
        log.info("CourseService::findByCourseId method Execution Ended for courseId: {}", courseId);
        return AppUtils.mapToResponse(courseEntity);
        // return courseList.stream().filter(course-> course.getCourseId()==courseId).findFirst().orElse( null);
    }

    //delete course  //DELETE
    public void deleteCourse(Integer courseId){
        log.info("CourseService::deleteCourse method Execution Started for courseId: {}", courseId);
         try{
             log.debug("CourseService::deleteCourse method inPut for courseId: {}", courseId);
             courseDao.deleteById(courseId);
         }catch (Exception e){
             log.error("CourseService::deleteCourse exception Occurs while deleting the course Object with courseId: {} " , courseId);
             throw new CourseServiceException("Deleting course failed: " + e.getMessage());
         }
        log.info("CourseService::deleteCourse method Execution Ended for courseId: {}", courseId);
    }


    //update the course //PUT
    public CourseResponseDTO updateCourse(int courseId, CourseRequestDTO courseRequest) {
        log.info("CourseService::updateCourse method Execution Started for courseId: {}", courseId);
        try {
            log.debug("CourseService::updateCourse method inPut for courseId: {} with request: {}", courseId, AppUtils.convertToJson(courseRequest));

            CourseEntity existingCourseEntity = courseDao.findById(courseId).orElseThrow(()->new RuntimeException(" Course Object is not present in db with id "+courseId));
            log.debug("CourseService::updateCourse existing course entity fetched from db: {}", AppUtils.convertToJson(existingCourseEntity));
            existingCourseEntity.setName(courseRequest.getName());
            existingCourseEntity.setTrainerName(courseRequest.getTrainerName());
            existingCourseEntity.setDuration(courseRequest.getDuration());
            existingCourseEntity.setStartDate(courseRequest.getStartDate());
            existingCourseEntity.setCourseType(courseRequest.getCourseType());
            existingCourseEntity.setFees(courseRequest.getFees());
            existingCourseEntity.setCertificateAvailable(courseRequest.isCertificateAvailable());
            existingCourseEntity.setDescription(courseRequest.getDescription());
            existingCourseEntity.setEmailId(courseRequest.getEmailId());
            existingCourseEntity.setContact(courseRequest.getContact());
            CourseEntity courseEntity = courseDao.save(existingCourseEntity);
            log.debug("CourseService::updateCourse course entity after update: {}", AppUtils.convertToJson(courseEntity));
            CourseResponseDTO courseResponseDTO = AppUtils.mapToResponse(courseEntity);
            log.info("CourseService::updateCourse method Execution Ended for courseId: {}", courseId);

            return courseResponseDTO;
        }
        catch (Exception e) {
            log.error("CourseService::updateCourse exception Occurs while updating the course Object with courseId: {} " , courseId);
            throw new CourseServiceException("Updating course failed: " + e.getMessage());
        }
    }

}
