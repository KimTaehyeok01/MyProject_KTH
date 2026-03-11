package com.alone.CourseRegistration.repository;

import com.alone.CourseRegistration.entity.CourseEntity;
import com.alone.CourseRegistration.entity.EnrollmentEntity;
import com.alone.CourseRegistration.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

    // 특정 학생 조회 방지
    List<EnrollmentEntity> findByStudentAndStatus(StudentEntity student, String status);

    boolean existsByStudentAndCourse(StudentEntity student, CourseEntity course);
}
