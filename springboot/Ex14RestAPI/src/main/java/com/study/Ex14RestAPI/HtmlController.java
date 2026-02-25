package com.study.Ex14RestAPI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {
    @GetMapping("/")
    public String index(){
        return "login"; // login.html로 응답.
    }
}
