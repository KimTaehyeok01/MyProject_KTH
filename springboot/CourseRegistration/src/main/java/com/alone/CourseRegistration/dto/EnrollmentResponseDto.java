package com.alone.CourseRegistration.dto;

import com.alone.CourseRegistration.entity.CourseEntity;
import com.alone.CourseRegistration.entity.EnrollmentEntity;
import com.alone.CourseRegistration.entity.StudentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EnrollmentResponseDto {
    private Long enrollmentId;
    private StudentEntity student;
    private CourseEntity course;
    private LocalDateTime enrolledAt;
    private String status;

    public EnrollmentResponseDto(EnrollmentEntity entity) {
        this.enrollmentId = entity.getEnrollmentId();
        this.student = entity.getStudent();
        this.course = entity.getCourse();
        this.enrolledAt = entity.getEnrolledAt();
        this.status = entity.getStatus();
    }
}
