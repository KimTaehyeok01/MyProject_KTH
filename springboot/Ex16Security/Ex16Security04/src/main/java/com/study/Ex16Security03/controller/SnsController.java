package com.study.Ex16Security03.controller;

import com.study.Ex16Security03.service.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Controller // 스프링 MVC에서 HTTP요청을 맨 처음 받아서 처리하는 어노테이션
@RequiredArgsConstructor
public class SnsController {
    private static final Set<String> SUPPORTED_PROVIDERS = Set.of("google", "kakao", "naver");
    private final OAuth2AuthorizedClientService authorizedClientService;

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

    // OAuth2 로그인 후 provider별 엑세스 토큰을 반환해주는 서블릿 메소드
    // 엑세스 토큰(JWT 토큰과 유사) : 인증된 사용자임을 알려주는 통행권
    // 유통기간 -> 구글 : 1시간, 카카오 : 12시간, 네이버 : 1시간, 깃허브 : 8시간
    @GetMapping("/token")
    @ResponseBody
    public String getDefaultToken(Authentication authentication) {
        return getToken("google", authentication);
    }

    @GetMapping("/token/{provider}")
    @ResponseBody
    public String getToken(@PathVariable String provider, Authentication authentication) {
        String normalizedProvider = provider.toLowerCase();
        if (!SUPPORTED_PROVIDERS.contains(normalizedProvider)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 provider 입니다.");
        }
        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        OAuth2AuthorizedClient authorizedClient = authorizedClientService
                .loadAuthorizedClient(normalizedProvider, authentication.getName());
        if (authorizedClient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "해당 provider로 로그인한 토큰이 없습니다: " + normalizedProvider);
        }

        return getTokenValue(authorizedClient, normalizedProvider);
    }

    private String getTokenValue(OAuth2AuthorizedClient authorizedClient, String providerName) {

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        //토큰 문자열
        String accessTokenString = accessToken.getTokenValue();
        // 발급 시간
        Instant issuedAt = accessToken.getIssuedAt();
        // 만료 시간
        Instant expiresAt = accessToken.getExpiresAt();
        // 현재 남은 시간 계산
        long remainSeconds = Duration.between(Instant.now(), expiresAt).getSeconds();

        System.out.println("[" + providerName + "] 발급 시간: " + issuedAt);
        System.out.println("[" + providerName + "] 만료 시간: " + expiresAt);
        System.out.println("[" + providerName + "] 남은 시간: " + remainSeconds + "초");

        return accessTokenString;
    }
}
