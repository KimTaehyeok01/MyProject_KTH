package com.study.LibraryStie.config;

import com.study.LibraryStie.domain.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String userId = authentication.getName();

        memberRepository.findByUserId(userId).ifPresent(member -> {
            request.getSession().setAttribute("userId", member.getUserId());
            request.getSession().setAttribute("userName", member.getUserName());
            request.getSession().setAttribute("userEmail", member.getEmail());
        });

        response.sendRedirect("/");
    }
}
