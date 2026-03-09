package com.postMemo.PostMemo;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final MemoService service;

    @GetMapping("/list")
    public List<MemoResponseDto> list(Model model){
        List<MemoResponseDto> list = service.findAll();
        return null;
    }

    @PostMapping("/add")
    public MemoRequestDto add(){
        return null;
    } 
}
