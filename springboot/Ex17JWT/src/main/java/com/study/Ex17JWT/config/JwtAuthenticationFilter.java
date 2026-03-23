package com.study.Ex17JWT.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

// 톰캣 WAS에 보안필터에 끼워지는 추가적인 필터
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
  private final JwtUtil jwtTokenProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    //HTTP 요청 헤더에서 JWT를 받아온다.
    String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
    //유효한 토큰인지 확인한다.
    if(token != null && jwtTokenProvider.validateToken(token)){
      //토큰이 유효하면 토큰으로부터 유저 정보를 가져온다.
      Authentication authentication =
          jwtTokenProvider.getAuthentication(token);
      //SecurityContext에 Authentication객체를 저장해 준다.
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }
}

