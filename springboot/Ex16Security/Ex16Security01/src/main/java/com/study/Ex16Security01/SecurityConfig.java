package com.study.Ex16Security01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;

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
        http // HTTP 요청에 대한 보안을 설정한다. Security 6버전.
                .authorizeHttpRequests((auth)->
                        // root("/) URL을 모두 접근 허용한다. permitAll()
                        auth.requestMatchers("/loginForm").permitAll()

                                // 로그인한 사용자만
                                .requestMatchers("/").authenticated()

                                // 그 외에 경로 요청(에: /mypage, /admin)에 대해서
                                // 인증된 사용자에게만 허용한다.
                                .anyRequest().authenticated()
                )
                .formLogin((formLogin)-> formLogin
                        // 이 설정 없으면 Spring Security 기본 로그인 페이지가 뜸
                        .loginPage("/loginForm") // 로그인폼 요청 URL

                        .usernameParameter("username") // HTML input의 name 속성을 알려주는 것
                        .passwordParameter("password") // HTML input의 name 속성을 알려주는 것

                        // loginAction에 대한 인증처리는 시큐리티가 다 한다. 코드가 필요없다.

                        // 로그인 처리를 어떤 URL로 할지" 설정하는 것
                        // 이 URL로 POST 요청이 오면 Spring Security가 자동으로 인증 처리
                        // 개발자가 Controller에 /loginAction 코드를 직접 안 짜도 됨!
                        .loginProcessingUrl("/loginAction") // 로그인 액션 요청 URL

                        .defaultSuccessUrl("/") // 로그인 성공시 리다이렉트
                        .permitAll() // 모두에게 허용
                );


        return http.build();
    }
}
