package com.study.LibraryStie.controller;

import com.study.LibraryStie.config.JwtUtil;
import com.study.LibraryStie.service.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SnsController {

    private final JwtUtil jwtUtil;

    @GetMapping("/snsLoginSuccess")
    @ResponseBody
    public String snsLoginSuccess(Authentication authentication, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");

        if (user != null) {
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            String jwtToken = jwtUtil.createToken(user.getEmail(), roles);
            session.setAttribute("JWT_TOKEN", jwtToken);
            session.setAttribute("userName", user.getName());
            session.setAttribute("userEmail", user.getEmail());
        }

        String userName = (user != null) ? user.getName() : "회원";
        return "<script>alert('" + userName + "님, 소셜 로그인 성공!'); location.href='/';</script>";
    }

    @GetMapping("/snsLoginFailure")
    @ResponseBody
    public String snsLoginFailure() {
        return "<script>alert('소셜 로그인에 실패했습니다. 다시 시도해 주세요.'); location.href='/login';</script>";
    }
}
