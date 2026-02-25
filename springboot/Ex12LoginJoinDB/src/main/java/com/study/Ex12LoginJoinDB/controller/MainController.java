package com.study.Ex12LoginJoinDB.controller;

import com.study.Ex12LoginJoinDB.dto.MemberLoginDto;
import com.study.Ex12LoginJoinDB.dto.MemberSaveDto;
import com.study.Ex12LoginJoinDB.entity.MemberEntity;
import com.study.Ex12LoginJoinDB.entity.MemberRepository;
import com.study.Ex12LoginJoinDB.service.LoginService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class MainController {
    private final MemberRepository memberRepository;
    private final LoginService loginService;

    @GetMapping("/")
    public String index() {
        // session 객체의 정보는 유효하다.
        return "index";
    }

    @GetMapping("/loginForm")
    public String loginForm() { // loginForm : 로그인 양식 요청
        return "loginForm";
    }

    // loginAction : 로그인 실제 처리
    // @Valid : Validation(유효성 체크)하도록 하는 어노테이션
    // BindingResult : 벨리데이션 결과값을 가진 객체
    // history.back()이 좋은 점: 로그인, 회원가입시 유저가 입력한 값이 그대로 남아있음.
    @PostMapping("/loginAction")
    @ResponseBody // 문자열로 반환
    public String loginAction(@Valid @ModelAttribute MemberLoginDto dto
            , BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) { // 바인딩 오류 발생
            //DTO에 설정한 message값을 가져온다.
            String detail = bindingResult.getFieldError().getDefaultMessage();
            System.out.println("detail: " + detail);
            // 스프링 서버에서 자바스크립트를 body에 응답값으로 보내면,
            // 웹브라우저가 이 자바스크립트를 수행해준다.
            return "<script>alert('" + detail + "'); history.back();</script>";
        }
        // 로그인 성공 로직
        session.setAttribute("isLogin", true);
        session.setAttribute("userId", dto.getUserId());
        // 프론트 컨트롤러의 코드가 길어지면 -> Service클래스(MVC)로 코드를 분리한다.
        String userRole = loginService.getUserRole(dto.getUserId());
        session.setAttribute("userRole", userRole);
        System.out.println("userRole = " + userRole);

        if(userRole.equals("ROLE_ADMIN")){
            return "<script>alert('로그인 성공!'); location.href='/admin';</script>";
        }
        else{
            return "<script>alert('로그인 성공!'); location.href='/';</script>";
        }
    }
    // 리다이렉트 : a태그, location.href, meta refresh
    //     - request, model에 데이터가 날라감.

    // 비회원 : 로그인 안한 사용자(단순 방문) - 쿠키에 사용자의 정보를 담는다.
    // 회원 : 로그인(회원가입)한 사용자 - DB, 세션객체에 데이터를 담는다.

    @GetMapping("/loginoutAction")
    public String logoutAction(HttpSession session){
        session.invalidate(); // 로그아웃
        return "redirect:/";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        List<MemberEntity> list = memberRepository.findAll();
        model.addAttribute("list", list);
        return "admin";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    // 회원가입 액션
    @PostMapping("/joinAction")
    // @ModelAttribute : 클라이언트가 보낸 http 요청 파라미터를 자바 클래스에 매핑하는 어노테이션.
    public String joinAction(@ModelAttribute MemberSaveDto saveDto) {
        // id는 null로 놔둔다.
        saveDto.setJoinDate(LocalDate.now()); // 현재 날짜로 설정.

        try {
            MemberEntity entity = saveDto.toSaveEntity();
            memberRepository.save(entity);
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    // 단건조회
    @GetMapping("/viewMember")
    // viewMember?id=3
    public String viewMember(@RequestParam("id") Long id, Model model) {
        Optional<MemberEntity> optional = memberRepository.findById(id);
        if (!optional.isPresent()) {
            return "redirect:/";
        }
        // null 아니면 람다식 실행
        optional.ifPresent((entity) -> {
            model.addAttribute("member", entity.toSaveDto());
        });
        return "modifyForm";
    }

    // 수정
    @RequestMapping(value = "/modifyAction", method = RequestMethod.POST)
    public String modifyAction(@ModelAttribute MemberSaveDto dto) {
        try {
            MemberEntity entity = dto.toSaveEntity();
            memberRepository.save(entity); // id값이 있으면 업데이트
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    // 삭제
    @RequestMapping(value = "/deleteMember", method = RequestMethod.GET)
    public String deleteMember(@RequestParam("id") Long id, Model model) {
        try {
            memberRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
}















