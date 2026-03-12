package com.study.Ex16Security03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String main(){
        return "index";
    }

    // 인증되지 않은 사용자여도 로그인,회원가입 페이지로는 접근 가능해야함.
    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }
}
