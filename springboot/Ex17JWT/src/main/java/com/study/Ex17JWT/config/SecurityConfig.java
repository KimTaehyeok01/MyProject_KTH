package com.study.Ex17JWT.config;

import com.study.Ex17JWT.util.JwtAuthenticationFilter;
import com.study.Ex17JWT.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/favicon.ico", "/api/users/signup", "/api/users/login").permitAll()
                        .requestMatchers("/api/users/admin", "/api/users/all").hasRole("ADMIN")
                        .requestMatchers("/api/users/mypage").authenticated()
                        .anyRequest().permitAll())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, e) ->
                                writeJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "인증이 필요합니다."))
                        .accessDeniedHandler((request, response, e) ->
                                writeJsonError(response, HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다.")));


        // JWT 토큰 기반 서버는 세션관리 토큰 기반으로 하기 때문에
        // 스프링 시큐리티에서 세션관리(html 기반) 정책을 관리안함 으로 설정한다.
        http.sessionManagement(sessionConfig -> sessionConfig
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // JWT 보안 필터를 특정 필터 앞에 추가한다.
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private void writeJsonError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"status\":" + status + ",\"message\":\"" + message + "\"}");
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
