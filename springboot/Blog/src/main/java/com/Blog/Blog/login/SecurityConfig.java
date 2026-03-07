package com.Blog.Blog.login;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;

    // BCrypt 비밀번호 암호화 Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 비활성화 (현재 GET 로그아웃 사용 중)
            .csrf(csrf -> csrf.disable())

            // 접균 권한 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/user/signup", "/loginRedirect").permitAll()
                .anyRequest().authenticated()
            )

            // 폼 로그인 설정
            .formLogin(login -> login
                .loginPage("/")                        // 로그인 페이지 URL
                .loginProcessingUrl("/login")          // POST 로그인 처리 URL
                .usernameParameter("userId")           // 폼 input name
                .passwordParameter("userPassword")     // 폼 input name
                .successHandler(successHandler)        // 로그인 성공 핸들러
                .failureUrl("/?error=true")            // 로그인 실패 시 이동
                .permitAll()
            )

            // 로그아웃 설정 (CSRF 비활성화로 GET 로그아웃 허용)
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // GET/POST 모두 허용
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            // UserDetailsService 연결
            .userDetailsService(userDetailsService);

        return http.build();
    }
}
