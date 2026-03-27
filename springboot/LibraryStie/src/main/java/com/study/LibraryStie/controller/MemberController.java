package com.study.LibraryStie.controller;

import com.study.LibraryStie.dto.MemberRequest;
import com.study.LibraryStie.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signupAction")
    @ResponseBody
    public String signupAction(@Valid @ModelAttribute MemberRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return "<script>alert('입력 오류: " + message + "'); history.back();</script>";
        }
        try {
            memberService.signUp(dto);
            return "<script>alert('회원가입이 완료되었습니다! 로그인해 주세요.'); location.href='/login';</script>";
        } catch (IllegalArgumentException e) {
            return "<script>alert('" + e.getMessage() + "'); history.back();</script>";
        } catch (Exception e) {
            e.printStackTrace();
            return "<script>alert('회원가입 중 오류가 발생했습니다.'); history.back();</script>";
        }
    }
}
