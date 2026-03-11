package com.alone.CourseRegistration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "course")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "course_id", nullable = false, unique = true, length = 20)
    private String courseId;

    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    @Column(name = "instructor", nullable = false, length = 50)
    private String instructor;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "course_time", nullable = false, length = 50)
    private String courseTime;

    @Column(name = "room", nullable = false, length = 50)
    private String room;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "enrolled")
    private Integer enrolled;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void increaseEnrolled() {
        this.enrolled = (this.enrolled == null ? 0 : this.enrolled) + 1;
    }

    public void decreaseEnrolled() {
        if (this.enrolled != null && this.enrolled > 0) this.enrolled--;
    }
}
