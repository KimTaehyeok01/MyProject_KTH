package com.alone.CourseRegistration.dto;

import com.alone.CourseRegistration.entity.CourseEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {
    private String courseId;
    private String courseName;
    private String instructor;
    private Integer credits;
    private String courseTime;
    private String room;
    private Integer capacity;
    private Integer enrolled;
    private LocalDate createdAt;

    @Builder
    public CourseEntity toSaveEntity(){
        return CourseEntity.builder()
                .courseId(this.courseId)
                .courseName(this.courseName)
                .instructor(this.instructor)
                .credits(this.credits)
                .courseTime(this.courseTime)
                .room(this.room)
                .capacity(this.capacity)
                .enrolled(this.enrolled)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
