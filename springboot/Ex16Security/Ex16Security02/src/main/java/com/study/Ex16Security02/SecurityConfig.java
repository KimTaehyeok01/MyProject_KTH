package com.study.Ex16Security02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;


// 시큐리티 관련 설정 클래스
@Configuration // 환경설정 클래스로 등록한다.
@EnableWebSecurity // 웹 보안 활성화 어노테이션
public class SecurityConfig {

    // authorizeHttpRequests() : HTTP 요청에 대한 접근 제어(인가)를 설정하는 핵심 메서드
    // requestMatchers() : 특정 경로에 대한 접근 규칙을 설정
    // authenticated() : 인증된 사용자만 허용
    // permitAll() : 모두에게 접근 허용
    // hasRole("ADMIN") : ROLE_ADMIN권한을 가진 사용자에게 허용
    // hasRole("USER") : 'ROLE_USER' 권한이 있으면 허용.
    // hasAnyRole("ADMIN", "USER"): 지정된 권한 중 하나라도 있으면 허용.
    // anyRequest().authenticated() : 나머지 요청은 로그인 필수.

    // SecurityFilterChain → HTTP 요청이 들어올 때마다 보안 체크를 하는 필터들의 묶음
    //쉽게 말해: "건물 입구에 보안 규칙을 설정하는 것"

    @Bean // 메소드 반환 객체를 빈으로 등록한다.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                // csrf 보안 설정을 비활성화(개발편의시)/활성화(기본)
                // .csrf(auth->auth.disable()) // csrf 비활성화
                // csrf 활성화(기본)
                // CSRF 보안 방식 2가지
                // 1. HttpSession 방식(기본) : 서버에 인증정보를 저장한다.
                // 2. CookieToken 방식 : (자바스크립트 기반 앱 제작시 쿠키에 csrfToken을 저장해야함)

                // 이 코드는 2번 방식임.
                .csrf(auth->auth
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())) // csrf 활성화

                .authorizeHttpRequests((auth)->
                        auth.requestMatchers("/loginForm").permitAll()
//                                .requestMatchers("/").authenticated()
                                .requestMatchers("/").permitAll()
                                .anyRequest().authenticated()
                )


                .formLogin((formLogin)-> formLogin
                        .loginPage("/loginForm")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/loginAction")
                        .defaultSuccessUrl("/")
                        // 로그인 성공 커스텀 핸들러
                        .successHandler((request, response, auth)->{
                            System.out.println("로그인 성공했습니다.");
                            response.sendRedirect("/");
                        })
                        // 로그인 실패 에러 페이지
                        .failureUrl("/loginForm?error=error")
                        .permitAll()
                )
                .logout(logout-> logout
                        .logoutRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET,"/logoutAction"))
//                        .logoutUrl("/logoutAction")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true) // 세션 객체 해제
                        .deleteCookies("JSESSIONID") // 쿠기 삭제

                );


        return http.build();
    }
}
