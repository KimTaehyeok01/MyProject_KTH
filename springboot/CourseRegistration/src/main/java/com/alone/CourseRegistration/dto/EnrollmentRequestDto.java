package com.alone.CourseRegistration.dto;

import com.alone.CourseRegistration.entity.CourseEntity;
import com.alone.CourseRegistration.entity.EnrollmentEntity;
import com.alone.CourseRegistration.entity.StudentEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequestDto {
    private Long enrollmentId;
    private StudentEntity student;
    private CourseEntity course;
    private LocalDateTime enrolledAt;
    private String status;

    @Builder
    public EnrollmentEntity toSaveEntity(){
        return EnrollmentEntity.builder()
                .enrollmentId(this.enrollmentId)
                .student(this.student)
                .course(this.course)
                .enrolledAt(this.enrolledAt)
                .status(this.status)
                .build();
    }
}
