package com.study.Ex16Security03.controller;

import com.study.Ex16Security03.dto.MemberRequest;
import com.study.Ex16Security03.dto.MemberResponse;
import com.study.Ex16Security03.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    // 응답할 때
    // 1. return "html파일이름" 타임리프나 JSP동적 HTML(뷰 템플릿)
    // 2. @ResponseBody : 문자열이나 JS, JSON, XML 문자열로 반환

    @PostMapping("/joinAction")
    @ResponseBody
    public String joinAction(@Valid @ModelAttribute MemberRequest dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return "<script>alert('에러메세지 : " + message + " '); location.href='/joinForm';</script>";
        }
        service.signUp(dto);
        return "<script>alert('회원가입 성공!'); location.href='/loginForm';</script>";
    }

    // 타임리프html에 데이터를 넘길 때 쓰는 객체
    // 1. Model 2. Request 3. Session x 4. Application x
    // Model : 순수하게 데이터
    // Request : model기능 + http요청에 대한 정보(헤더, 바디)를 다 가짐.
    // Session : 로그인 했을 때 -> 로그아웃까지의 데이터(기억)를 가짐.
    //          예) 저장해야 할 정보는? : 로그인 아이디/전체 앱에서 필요한 정보(프로필 이미지,아이디, 로그인 여부)
    // Application : 웹브라우저 닫을때까지 정보 보관. 전체 앱에서 사용할 때
    //       사용자 PC에 로컬에 저장
    //   1. 쿠키 : Key-Value의 고전적인 방식
    //   2. localStorage : Key-Value의 현대적 방식
    @GetMapping("/viewDTO")
    public String viewDTO(@RequestParam Long id, Model model)  {
        model.addAttribute("member", service.findById(id));
        return "modifyForm";
    }

    @PostMapping("/modifyAction")
    @ResponseBody
    public String modifyAction(@RequestParam Long id, @ModelAttribute MemberRequest dto) {
        try {
            service.update(id, dto);
            return "<script>alert('수정 성공!'); location.href='/admin';</script>";
        }
        catch (Exception e){
            e.printStackTrace();
            return "<script>alert('수정 실패!'); history.back();</script>";
        }
    }

    @PostMapping("/delete")
    public String deleteDTO(@RequestParam Long id){
        service.delete(id);
        return "redirect:/admin";
    }
}
