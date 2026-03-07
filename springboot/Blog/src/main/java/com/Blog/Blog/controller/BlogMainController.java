package com.Blog.Blog.controller;

import com.Blog.Blog.dto.BlogRequestDto;
import com.Blog.Blog.dto.BlogResponseDto;
import com.Blog.Blog.entity.AddUserRequest;
import com.Blog.Blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BlogMainController {
    private final BlogService service;

    // 첫 페이지
    @GetMapping("/")
    public String main() {
        return "login";
    }

    // 회원가입 페이지 이동
    @GetMapping("/user/signup")
    public String userSignUp() {
        return "signUp";
    }

    // 회원가입
    @PostMapping("/user/signup")
    @ResponseBody
    public String signUp(@ModelAttribute AddUserRequest request) {
        if (request.getUserId() == null || request.getUserId().trim().isEmpty()) {
            return "<script>alert('아이디를 입력해주세요.'); history.back();</script>";
        }
        if (request.getUserPassword() == null || request.getUserPassword().trim().isEmpty()) {
            return "<script>alert('비밀번호를 입력해주세요.'); history.back();</script>";
        }
        try {
            service.save(request);
            return "<script>alert('회원가입이 완료되었습니다! 로그인 해주세요.'); location.href='/';</script>";
        } catch (Exception e) {
            return "<script>alert('가입 실패: " + e.getMessage() + "'); history.back();</script>";
        }
    }

    // 회원가입P -> 로그인P
    @GetMapping("/loginRedirect")
    public String loginRedirect() {
        return "redirect:/";
    }

    // 로그인
    // 로그인 폼 제출은 Spring Security가 자동 처리 (/login POST)
    // 로그인 성공 시 CustomAuthenticationSuccessHandler에서 세션 설정

    // 메인 페이지
    @GetMapping("/mainBlogPage")
    public String mainBlogPage(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        int safePage = Math.max(page, 0);
        Page<BlogResponseDto> paging = service.getList(safePage);
        model.addAttribute("paging", paging);

        return "mainBlogPage";
    }

    // 제목 상세 보기
    @GetMapping("/blog/{id}")
    public String blogTitleDetail(@PathVariable Long id, Model model){
        model.addAttribute("blog", service.findById(id));
        return "blogTitleDetail";
    }

    // 글작성 페이지로 이동
    @GetMapping("/blog/new")
    public String blogAddDirect(Model model) {
        model.addAttribute("blog", new BlogRequestDto());
        return "addForm";
    }

    // 글 작성
    @PostMapping("/blog/add")
    public String blogAdd(@ModelAttribute BlogRequestDto dto) {
        service.save(dto);
        return "redirect:/mainBlogPage";
    }

    // 글 수정 페이지 이동
    @GetMapping("/blog/{id}/edit")
    public String editFormDirect(@PathVariable Long id, Model model) {
        model.addAttribute("blog", service.findById(id));
        return "edit";
    }

    // 글 수정하기
    @PostMapping("/blog/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute BlogRequestDto dto) {
        service.update(id, dto);
        return "redirect:/mainBlogPage";
    }

    // 삭제하기
    @GetMapping("/blog/{id}/delete")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "redirect:/mainBlogPage";
    }
}
