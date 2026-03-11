package com.alone.CourseRegistration;

import com.alone.CourseRegistration.dto.CourseResponseDto;
import com.alone.CourseRegistration.dto.EnrollmentResponseDto;
import com.alone.CourseRegistration.entity.CourseEntity;
import com.alone.CourseRegistration.entity.EnrollmentEntity;
import com.alone.CourseRegistration.entity.StudentEntity;
import com.alone.CourseRegistration.repository.CourseRepository;
import com.alone.CourseRegistration.repository.EnrollmentRepository;
import com.alone.CourseRegistration.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    // 전체 강좌 목록 조회
    public List<CourseResponseDto> findAll() {
        return courseRepository.findAll()
                .stream().map(CourseResponseDto::new).collect(Collectors.toList());
    }

    // 내 수강 목록 조회
    public List<EnrollmentResponseDto> findEnrolledCourses(String studentId) {
        StudentEntity student = studentRepository.findByStudentId(studentId).orElse(null);
        if (student == null) return Collections.emptyList();
        return enrollmentRepository.findByStudentAndStatus(student, "ENROLLED")
                .stream().map(EnrollmentResponseDto::new).collect(Collectors.toList());
    }

    // 신청한 강좌
    public Set<String> findEnrolledCourseIds(String studentId) {
        StudentEntity student = studentRepository.findByStudentId(studentId).orElse(null);
        if (student == null) return Collections.emptySet();
        return enrollmentRepository.findByStudentAndStatus(student, "ENROLLED")
                .stream().map(e -> e.getCourse().getCourseId()).collect(Collectors.toSet());
    }

    // 총 학점 계산
    public int getTotalCredits(String studentId) {
        StudentEntity student = studentRepository.findByStudentId(studentId).orElse(null);
        if (student == null) return 0;
        return enrollmentRepository.findByStudentAndStatus(student, "ENROLLED")
                .stream().mapToInt(e -> e.getCourse().getCredits()).sum();
    }

    // 수강신청
    @Transactional
    public void enroll(String studentId, String courseId) {
        StudentEntity student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다: " + studentId));
        CourseEntity course = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new IllegalArgumentException("강좌를 찾을 수 없습니다: " + courseId));

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) return;
        if (course.getEnrolled() >= course.getCapacity()) return;

        EnrollmentEntity enrollment = EnrollmentEntity.builder()
                .student(student)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .status("ENROLLED")
                .build();
        enrollmentRepository.save(enrollment);
        course.increaseEnrolled();
    }

    // 수강취소
    @Transactional
    public void cancel(Long enrollmentId) {
        EnrollmentEntity enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("수강신청 내역을 찾을 수 없습니다."));
        enrollment.getCourse().decreaseEnrolled();
        enrollmentRepository.delete(enrollment);
    }
}
