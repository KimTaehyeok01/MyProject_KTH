package com.Blog.Blog.login;

import com.Blog.Blog.entity.BlogUserInfoRepository;
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

    private final BlogUserInfoRepository blogUserInfoRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Spring Security 인증이 성공하면 userId로 DB에서 유저 정보를 가져와 세션에 저장
        String userId = authentication.getName();

        blogUserInfoRepository.findByUserId(userId).ifPresent(user -> {
            request.getSession().setAttribute("userId", user.getUserId());
            request.getSession().setAttribute("userName", user.getUserName());
        });

        setDefaultTargetUrl("/mainBlogPage");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
