package com.spring.validation_exception.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.validation_exception.Entity.CourseEntity;
import com.spring.validation_exception.dto.CourseRequestDTO;
import com.spring.validation_exception.dto.CourseResponseDTO;

public class AppUtils {


    public static CourseEntity mapToEntity(CourseRequestDTO courseRequest) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(courseRequest.getName());
        courseEntity.setTrainerName(courseRequest.getTrainerName());
        courseEntity.setDuration(courseRequest.getDuration());
        courseEntity.setStartDate(courseRequest.getStartDate());
        courseEntity.setCourseType(courseRequest.getCourseType());
        courseEntity.setFees(courseRequest.getFees());
        courseEntity.setCertificateAvailable(courseRequest.isCertificateAvailable());
        courseEntity.setDescription(courseRequest.getDescription());
        courseEntity.setEmailId(courseRequest.getEmailId());
        courseEntity.setContact(courseRequest.getContact());
        return courseEntity;
    }

    public static CourseResponseDTO mapToResponse(CourseEntity courseEntity) {
        CourseResponseDTO courseResponse = new CourseResponseDTO();
        courseResponse.setCourseId(courseEntity.getCourseId());
        courseResponse.setName(courseEntity.getName());
        courseResponse.setTrainerName(courseEntity.getTrainerName());
        courseResponse.setDuration(courseEntity.getDuration());
        courseResponse.setStartDate(courseEntity.getStartDate());
        courseResponse.setCourseType(courseEntity.getCourseType());
        courseResponse.setFees(courseEntity.getFees());
        courseResponse.setCertificateAvailable(courseEntity.isCertificateAvailable());
        courseResponse.setDescription(courseEntity.getDescription());
        courseResponse.setEmailId(courseEntity.getEmailId());
        courseResponse.setContact(courseEntity.getContact());

        return courseResponse;
    }

    public static String convertToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
