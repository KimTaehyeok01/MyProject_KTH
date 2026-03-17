package com.study.Ex16Security03.controller;

import com.study.Ex16Security03.dto.MemberResponse;
import com.study.Ex16Security03.entity.Member;
import com.study.Ex16Security03.entity.MemberRepository;
import com.study.Ex16Security03.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final MemberRepository repository;
    private final MemberService service;

    @GetMapping("/")
    public String main(){
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("listCount", repository.count());
        model.addAttribute("list", service.findAll());
        return "admin";
    }
}
