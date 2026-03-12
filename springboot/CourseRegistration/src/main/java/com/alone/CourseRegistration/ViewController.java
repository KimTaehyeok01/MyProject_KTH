package com.alone.CourseRegistration;

import com.alone.CourseRegistration.dto.CourseRequestDto;
import com.alone.CourseRegistration.dto.CourseResponseDto;
import com.alone.CourseRegistration.entity.CourseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final CourseService courseService;
    private static final String TEMP_STUDENT_ID = "STU001";

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("enrolledCourses", courseService.findEnrolledCourses(TEMP_STUDENT_ID));
        model.addAttribute("enrolledCourseIds", courseService.findEnrolledCourseIds(TEMP_STUDENT_ID));
        model.addAttribute("totalCredits", courseService.getTotalCredits(TEMP_STUDENT_ID));
        return "index";
    }

    // 신청
    @PostMapping("/enroll")
    public String enroll(@RequestParam("courseId") String courseId) {
        courseService.enroll(TEMP_STUDENT_ID, courseId);
        return "redirect:/";
    }

    // 취소
    @PostMapping("/cancel")
    public String cancel(@RequestParam("enrollmentId") Long enrollmentId) {
        courseService.cancel(enrollmentId);
        return "redirect:/";
    }

    // 관리 페이지 이동
    @GetMapping("/manage/edit")
    public String courseHandle(Model model){
        model.addAttribute("courses", courseService.findAll());
        return "manage";
    }

    // 추가
    @PostMapping("/manage/add")
    public String edit(@ModelAttribute CourseRequestDto courseRequestDto){
        courseService.save(courseRequestDto);
        return "redirect:/manage/edit";
    }

    // 삭제
    @PostMapping("/manage/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        courseService.delete(id);
        return "redirect:/manage/edit";
    }

    // 수정 폼 불러오기
    @GetMapping("/manage/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("course", courseService.findById(id));
        return "course-edit";
    }

    // 수정 처리
    @PostMapping("/manage/edit/{id}")
    public String editSubmit(@PathVariable("id") Long id,
                             @ModelAttribute CourseRequestDto courseRequestDto){
        courseService.update(id, courseRequestDto);
        return "redirect:/manage/edit";
    }
}
