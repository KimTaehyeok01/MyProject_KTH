package com.study.Ex17JWT.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                // Rest API 서버에서는 주로 비활성화 한다.
                // 인증을 폼 기반으로 하지 않고, token(JWT)기반으로 하기에, csrf 인증이 필요하다.
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // "/**" 하위 경로 포함 모든 루트 경로의 인가를 풀어준다.
                        .requestMatchers("/**").permitAll());

        return http.build();
    }
}
