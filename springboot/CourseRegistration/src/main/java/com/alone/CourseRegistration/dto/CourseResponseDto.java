package com.alone.CourseRegistration.dto;

import com.alone.CourseRegistration.entity.CourseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class CourseResponseDto {
    private Long id;
    private String courseId;
    private String courseName;
    private String instructor;
    private Integer credits;
    private String courseTime;
    private String room;
    private Integer capacity;
    private Integer enrolled;
    private LocalDateTime createdAt;

    public CourseResponseDto(CourseEntity entity) {
        this.id = entity.getId();
        this.courseId = entity.getCourseId();
        this.courseName = entity.getCourseName();
        this.instructor = entity.getInstructor();
        this.credits = entity.getCredits();
        this.courseTime = entity.getCourseTime();
        this.room = entity.getRoom();
        this.capacity = entity.getCapacity();
        this.enrolled = entity.getEnrolled();
        this.createdAt = entity.getCreatedAt();
    }
}
