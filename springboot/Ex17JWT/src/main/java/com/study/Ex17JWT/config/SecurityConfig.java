package com.study.Ex17JWT.config;

import com.study.Ex17JWT.util.JwtAuthenticationFilter;
import com.study.Ex17JWT.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

// @Configuration : 해당 객체를 설정 클래스로 선언하는 어노테이션
// @Bean : 필요한 객체를 외부로부터 주입하는 어노테이션
@Configuration
@EnableWebSecurity

// @EnableMethodSecurity : 메소드 호출전에 보안을 활성화
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                // Rest API 서버에서는 주로 비활성화 한다.
                // 인증을 폼 기반으로 하지 않고, token(JWT)기반으로 하기에, csrf 인증이 필요하다.
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // "/**" 하위 경로 포함 모든 루트 경로의 인가를 풀어준다.
                        .requestMatchers("/**").permitAll());


        // JWT 토큰 기반 서버는 세션관리 토큰 기반으로 하기 때문에
        // 스프링 시큐리티에서 세션관리(html 기반) 정책을 관리안함 으로 설정한다.
        http.sessionManagement(sessionConfig -> sessionConfig
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // JWT 보안 필터를 특정 필터 앞에 추가한다.
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    //CORS 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Collections.singletonList("*")); // 허용할 HTTP header
        config.setAllowedMethods(Collections.singletonList("*")); // 허용할 HTTP method
        config.setAllowedOriginPatterns(Collections.singletonList("*")); // 허용할 출처
        //config.setAllowedOriginPatterns(Collections.singletonList("http://127.0.0.1:8080")); // 허용할 출처
        config.setAllowCredentials(true); // 쿠키 인증 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
