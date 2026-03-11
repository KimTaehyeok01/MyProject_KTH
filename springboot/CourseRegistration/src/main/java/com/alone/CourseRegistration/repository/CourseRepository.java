package com.alone.CourseRegistration.repository;

import com.alone.CourseRegistration.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    Optional<CourseEntity> findByCourseId(String courseId);
}
