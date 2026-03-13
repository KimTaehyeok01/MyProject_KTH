package com.study.Ex16Security03.controller;

import com.study.Ex16Security03.dto.MemberRequest;
import com.study.Ex16Security03.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 프론트 -> Http요청 -> Controller -> Service(로직) -> DTO
// -> Repository -> Entity -> DB

// DBMS -> Entity -> Repository -> Service -> DTO -> Controller
// ->(View Resolver)html -> 타임리프 -> html응답
// JS(React) -> DTO -> JSON문자열(시리얼라이즈) -> Http body 응답

// 회원(로그인, 회원가입) 요청에 대한 처리
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService service;

    // 인증되지 않은 사용자여도 로그인,회원가입 페이지로 접근 가능해야함.
    @GetMapping("/loginForm")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    // @RequestParam - GET방식(주소줄 KV), POST방식(BODY에)
    //   데이터 형식 : application/x-www-form-urlencoded
    //   데이터 모양 : name=hong&age=20
    // 주로 form태그에서 보낼 때

    // @RequestBody - GET방식은 못보냄. POST방식(BODY에)
    //   데이터 형식 : application/json
    //   데이터 모양 : {"name" : "hong", "age" : "20"}
    // 주로 JS(리액트)에서 보낼 때, RestAPI Server에서 받을 때

    // @ModelAttribute - 클래스와 맵과 매핑(바인딩)해주는 어노테이션
    // ORM : Entity와 DB 테이블과 매핑할 때
    // @DateTimeFormat - 데이터 바인딩

    // @PathVariable : 경로에 있는 문구를 변수로 변환하는 것
    //  localhost:8080/api/post/@{id}
    // getPost(@PathVariable("id") Long postId)

    @PostMapping("/joinAction")
    @ResponseBody
    public String joinAction(@Valid @ModelAttribute MemberRequest dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return "<script>alert('에러메세지 : " + message + " '); location.href='/joinForm';</script>";
        }
        service.joinAction(dto);
        return "<script>alert('회원가입 성공!'); location.href='/loginForm';</script>";
    }
}
