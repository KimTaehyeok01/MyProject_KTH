package com.study.Ex17JWT.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
// @Secured : 메소드 호출전에 보안을 활성화
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtUtil jwtUtil;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        //RestAPI 서버에서는 주로 비활성화한다.
        //인증을 폼 기반으로 하지 않고, 토큰(JWT)기반으로 하기에, CSRF 인증이 불필요하다.
        .csrf(csrf -> csrf.disable());

    http
        .authorizeHttpRequests((authz) -> authz
            // "/**" 하위 경로 포함, 모든 루트 경로의 인가를 풀어준다.
            .requestMatchers("/**")
            .permitAll());

    //스프링 시큐리티에서 세션관리(html기반) 정책을 관리안함 으로 설정한다.
    //JWT 토큰 기반 서버는 세션관리는 토큰으로 하기 때문에
    http.sessionManagement(sessionConfig -> sessionConfig
        .sessionCreationPolicy(
            SessionCreationPolicy.STATELESS
        )
    );
    //JWT 보안 필터를 특정 필터 앞에 추가한다.
    http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil),
        UsernamePasswordAuthenticationFilter.class
    );

    return http.build();
  }
  //CORS 설정

  //CORS 설정
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedHeaders(Collections.singletonList("*")); // 허용할 HTTP header
    config.setAllowedMethods(Collections.singletonList("*")); // 허용할 HTTP method
//    config.setAllowedOriginPatterns(Collections.singletonList("*")); // 허용할 출처
    config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:5173")); // 허용할 출처
    config.setAllowCredentials(true); // 쿠키 인증 요청 허용

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

}//class
