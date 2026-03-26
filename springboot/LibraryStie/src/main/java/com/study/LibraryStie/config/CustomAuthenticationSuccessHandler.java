package com.study.LibraryStie.config;

import com.study.LibraryStie.entity.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// 일반 로그인 성공 핸들러
// 로그인 성공 시 JWT 토큰 발급 + 세션에 사용자 정보 저장
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String userId = authentication.getName();

        // 회원 정보 조회 후 세션 저장
        memberRepository.findByUserId(userId).ifPresent(member -> {
            request.getSession().setAttribute("userId", member.getUserId());
            request.getSession().setAttribute("userName", member.getUserName());
            request.getSession().setAttribute("userEmail", member.getEmail());
        });

        // JWT 토큰 발급 후 세션 저장
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String jwtToken = jwtUtil.createToken(userId, roles);
        request.getSession().setAttribute("JWT_TOKEN", jwtToken);

        // 메인 페이지로 이동
        response.sendRedirect("/");
    }
}
