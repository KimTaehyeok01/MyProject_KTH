package com.example.Ex09Model;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// 스프링 프론트 컨트롤러 클래스가 여러개 있을 수 있다
// MainController, LoginController, QnAController
// 단 경로만 겹치지만 않으면 된다. 핸들러 맵퍼가 알아서 라우팅한다.
@Controller
public class ParamController {
    @GetMapping("/main")
    public String main() {
        return "redirect:loginForm";
    }

    // redirect : 응답헤더에 리다이렉트할 주소를 넣어서 응답한다.
    // 그러면, 웹브라우저가 그 주소를 보고, 다시 요청한다.
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/loginAction1")
    public String loginAction(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        return "loginResult";
    }

    @PostMapping("loginAction4")
    public String loginAction4(String username, @RequestParam("password") String bibun, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("password", bibun);
        return "loginResult";
    }

    @PostMapping("/loginAction2")
    public String loginAction2(Member member, Model model) {
        model.addAttribute("member", member);
        return "loginResult2";
    }

    // RequestParam : 클라이언트가 보낸 요청파라미터를 컨트롤러 메소드의 변수에 1대1 매핑해주는 어노테이션
    @PostMapping("/loginAction3")
    public String loginAction3(@RequestParam Map<String, Object> map, Model model) {
        model.addAttribute("map", map);
        return "loginResult3";
    }

    // @RequestMapping : 어떤 타입의 요청이라도 받아들인다.
    // @PathVariable : 호출경로를 매개변수로 받을 수 있다.
    // URL : localhost:8080//loginAction1/hong/1234
    @RequestMapping("/loginAction1/{pathVar1}/{pathVar2}")
    public String loginAction1(@PathVariable("pathVar1") String id,
                               @PathVariable("pathVar2") String pw,
                               Model model) {
        model.addAttribute("username", id);
        model.addAttribute("password", pw);
        return "loginResult";
    }
}












