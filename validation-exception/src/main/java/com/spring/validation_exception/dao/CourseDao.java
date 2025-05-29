package com.spring.validation_exception.dao;

import com.spring.validation_exception.Entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends JpaRepository<CourseEntity, Integer> {

}
