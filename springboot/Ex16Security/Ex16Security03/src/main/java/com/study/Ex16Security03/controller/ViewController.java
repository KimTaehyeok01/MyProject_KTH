package com.study.Ex16Security03.controller;

import com.study.Ex16Security03.entity.Member;
import com.study.Ex16Security03.entity.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {
    @Autowired
    private MemberRepository repository;

    @GetMapping("/")
    public String main(){
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("listCount", repository.count());

        List<Member> list = repository.findAll();
        model.addAttribute("list", list);
        return "admin";
    }
}
