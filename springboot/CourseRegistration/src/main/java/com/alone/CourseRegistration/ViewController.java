package com.alone.CourseRegistration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/enroll")
    public String enroll(@RequestParam String courseId) {
        courseService.enroll(TEMP_STUDENT_ID, courseId);
        return "redirect:/";
    }

    @PostMapping("/cancel")
    public String cancel(@RequestParam Long enrollmentId) {
        courseService.cancel(enrollmentId);
        return "redirect:/";
    }
}
