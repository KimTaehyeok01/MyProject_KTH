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
        String userId = authentication.getName();

        blogUserInfoRepository.findByUserId(userId).ifPresent(user -> {
            request.getSession().setAttribute("userId", user.getUserId());
            request.getSession().setAttribute("userName", user.getUserName());
        });

        setDefaultTargetUrl("/mainBlogPage");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
