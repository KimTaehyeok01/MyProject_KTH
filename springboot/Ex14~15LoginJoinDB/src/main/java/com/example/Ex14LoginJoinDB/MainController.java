package com.example.Ex14LoginJoinDB;

import com.example.Ex14LoginJoinDB.Dto.MemberLoginDto;
import com.example.Ex14LoginJoinDB.Dto.MemberRequestDto;
import com.example.Ex14LoginJoinDB.Dto.MemberResponseDto;
import com.example.Ex14LoginJoinDB.Entity.MemberEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MemberLoginService service;

    // 로그인 페이지
    @GetMapping("/")
    public String main() {
        return "login";
    }

    // 로그인 하기
    @PostMapping("/loginAction")
    @ResponseBody
    public String loginAction(@ModelAttribute MemberLoginDto dto, HttpSession session) {
        try {
            MemberResponseDto loginMember = service.loginCheck(dto.getMemberUserName(), dto.getMemberPassword());
            session.setAttribute("member_username", dto.getMemberUserName());
            session.setAttribute("member_role", dto.getMemberRole());

            if("ROLE_ADMIN".equals(loginMember.getMemberRole())){
                return "<script>alert('로그인 성공!'); location.href='/main';</script>";
            }
            else{
                return "<script>alert('로그인 성공!'); location.href='/userPage';</script>";
            }

        } catch (IllegalArgumentException e) {
            return "<script>alert('" + e.getMessage() + "'); history.back();</script>";
        }
    }

    // 회원가입 페이지 이동
    @GetMapping("/joinForm")
    public String joinForm() {
        return "signUp";
    }

    // 회원가입 하기
    @PostMapping("/joinAction")
    @ResponseBody
    public String joinAction(@ModelAttribute MemberRequestDto dto) {
        if (dto.getMemberUserName() == null || dto.getMemberUserName().trim().isEmpty()) {
            return "<script>alert('아이디를 입력해주세요.'); history.back();</script>";
        }
        if (dto.getMemberPassword() == null || dto.getMemberPassword().trim().isEmpty()) {
            return "<script>alert('비밀번호를 입력해주세요.'); history.back();</script>";
        }
        try {
            service.save(dto);
            return "<script>alert('회원가입이 완료되었습니다! 로그인 해주세요.'); location.href='/';</script>";

        } catch (Exception e) {
            return "<script>alert('가입 실패: " + e.getMessage() + "'); history.back();</script>";
        }
    }

    // 회원페이지 이동
    @GetMapping("/userPage")
    public String userPage() {
        return "userPage";
    }

    // 로그인 후 관리자 페이지
    @GetMapping("/main")
    public String main(Model model) {
        List<MemberResponseDto> list = service.findAll();
        model.addAttribute("memberList", list);
        return "memberList";
    }

    // 수정페이지 이동
    @GetMapping("/memberEditForm")
    public String memberEditForm(@RequestParam Integer id, Model model){
        model.addAttribute("member", service.findById(id));
        return "memberEditForm";
    }

    // 수정하기
    @PostMapping("/memberUpdate")
    public String memberUpdate(@RequestParam Integer memberNo, @ModelAttribute MemberRequestDto dto){
        service.update(memberNo,dto);
        return "redirect:/main";
    }

    // 삭제하기
    @GetMapping("/memberDelete")
    public String memberDelete(@RequestParam Integer id){
        service.delete(id);
        return "redirect:/main";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(){
        return "redirect:/";
    }
}









