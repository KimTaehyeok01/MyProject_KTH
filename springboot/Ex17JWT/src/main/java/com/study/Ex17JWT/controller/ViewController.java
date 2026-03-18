package com.study.Ex17JWT.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// html을 응답하는 컨트롤러 클래스
@Controller
public class ViewController {
    @GetMapping("/")
    public String main(){
        return "apiForm";
    }
}
