package com.study.LibraryStie.config;

import com.study.LibraryStie.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler customSuccessHandler;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 설정
                .csrf((CsrfConfigurer<HttpSecurity> csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))

                // 경로별 인가 설정
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/", "/login", "/loginAction", "/signup", "/signupAction").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        .requestMatchers("/api/books/**").permitAll()        // 도서 조회 API
                        .requestMatchers("/snsLoginSuccess", "/snsLoginFailure").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // 일반 로그인 설정
                .formLogin((FormLoginConfigurer<HttpSecurity> login) -> login
                        .loginPage("/login")                    // 로그인 페이지
                        .usernameParameter("userId")            // 아이디 필드명
                        .passwordParameter("password")          // 비밀번호 필드명
                        .loginProcessingUrl("/loginAction")     // 시큐리티가 자동 처리
                        .successHandler(customSuccessHandler)   // 로그인 성공 핸들러 (JWT 발급)
                        .failureUrl("/login?error=error")       // 로그인 실패 시
                        .permitAll()
                )

                // 로그아웃 설정
                .logout((LogoutConfigurer<HttpSecurity> logout) -> logout
                        .logoutUrl("/logoutAction")             // POST 방식 추천
                        .logoutSuccessUrl("/")                  // 로그아웃 후 메인으로
                        .invalidateHttpSession(true)            // 세션 삭제
                        .deleteCookies("JSESSIONID")            // 쿠키 삭제
                )

                // 소셜 로그인 설정 (Kakao, Naver)
                .oauth2Login((OAuth2LoginConfigurer<HttpSecurity> oauth) -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // 커스텀 OAuth2 서비스
                        )
                        .successHandler(snsSuccessHandler())
                        .failureHandler(snsFailureHandler())
                )

                // JWT 인증 필터 추가 (UsernamePasswordAuthenticationFilter 앞에 실행)
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement(session->session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    // SNS 로그인 성공 시 /snsLoginSuccess 로 이동
    @Bean
    SimpleUrlAuthenticationSuccessHandler snsSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/snsLoginSuccess");
    }

    // SNS 로그인 실패 시 /snsLoginFailure 로 이동
    @Bean
    SimpleUrlAuthenticationFailureHandler snsFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/snsLoginFailure");
    }
}
