package com.study.Ex16Security03.controller;

import com.study.Ex16Security03.service.SessionUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.time.Instant;

@Controller // 스프링 MVC에서 HTTP요청을 맨 처음 받아서 처리하는 어노테이션
public class SnsController {

    @GetMapping("/snsLoginSuccess")
    @ResponseBody
    public String snsLoginSuccess(Model model, HttpSession session) {
        // session 객체에 "user" 라는 SNS 유저 정보를 저장한 것을 가져온다.
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userName = "";
        String userEmail = "";
        String userPicture = "";
        if (user != null) {
            userName = user.getName();
            userEmail = user.getEmail();
            userPicture = user.getPicture();

            //member_security테이블에 sns_user정보를 insert한다.
            // 원본 회원가입 테이블과 병합한다.
        }
        return "<script>alert('SNS로그인성공 " + userName + "'); location.href='/';</script>";
    }

    @GetMapping("/snsLoginFailure")
    @ResponseBody
    public String snsLoginFailure() {
        return "<script>alert('SNS로그인실패'); history.back();</script>";
    }

    // Google OAuth2 로그인 후 엑세스 토큰을 반환해주는 서블릿 메소드
    // 엑세스 토큰(JWT 토큰과 유사) : 인증된 사용자임을 알려주는 통행권
    // 유통기간 -> 구글 : 1시간, 카카오 : 12시간, 네이버 : 1시간, 깃허브 : 8시간
    @GetMapping("/token")
    @ResponseBody
    public String getToken(@RegisteredOAuth2AuthorizedClient("google")
                           OAuth2AuthorizedClient authorizedClient) {

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        //토큰 문자열
        String accessTokenString = accessToken.getTokenValue();
        // 발급 시간
        Instant issuedAt = accessToken.getIssuedAt();
        // 만료 시간
        Instant expiresAt = accessToken.getExpiresAt();
        // 현재 남은 시간 계산
        long remainSeconds = Duration.between(Instant.now(), expiresAt).getSeconds();

        System.out.println("발급 시간: " + issuedAt);
        System.out.println("만료 시간: " + expiresAt);
        System.out.println("남은 시간: " + remainSeconds + "초");

        return accessTokenString;
    }
}
