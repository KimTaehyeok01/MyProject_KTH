package com.study.Ex16Security03.config;

import com.study.Ex16Security03.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


// 시큐리티 관련 설정 클래스
@Configuration // 환경설정 클래스로 등록한다.
@EnableWebSecurity // 웹 보안 활성화 어노테이션
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    // 시큐리티 기본암호화 객체
    // BCrypt 암호화 엔코더
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 스프링 시큐리티 6.4.x에서 공식 지원하는 PasswordEncoder 구현 클래스들
        // BCrypt, Argon2, Pbkdf2, SCrypt
        // 암호화 강도는 4 ~ 31까지 지정 가능. (몇 번 섞는가?) 기본 강도는 10이다.

        return new BCryptPasswordEncoder(12);
        // return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        // return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        // return SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8();

        // return new Pbkdf2Password4jPasswordEncoder();
        // Password4j는 외부라이브러리를 이용한다. 공식이 아니므로 비추천. 시큐리티 7에 추가.
    }

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // csrf 보안 설정을 비활성화(개발편의시)/활성화(기본)
                // .csrf(auth->auth.disable()) // csrf 비활성화
                // csrf 활성화(기본)
                // CSRF 보안 방식 2가지
                // 1. HttpSession 방식(기본) : 서버에 인증정보를 저장한다.
                // 2. CookieToken 방식 : (자바스크립트 기반 앱 제작시 쿠키에 csrfToken을 저장해야함)

                // 이 코드는 2번 방식임.
                // csrf 설정 : 람다매개변수 타입은 생략 가능함. 타입추정
                // CsrfConfigurer<HttpSecurity>가 생략된것임.
                .csrf((CsrfConfigurer<HttpSecurity> csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())) // csrf 활성화

                // 경로별 인가 설정
                // authz : Authorization(인가), authn : Authentication
                // AuthorizationManagerRequestMatcherRegistry 타입이다.
                .authorizeHttpRequests((authz) -> authz
//                                .requestMatchers("/", "/loginForm").permitAll()
                                .requestMatchers("/", "/loginForm", "/joinForm", "/joinAction").permitAll()

//                                .requestMatchers("/loginForm").permitAll()
//                                .requestMatchers("/joinForm").permitAll()
//                                .requestMatchers("/joinAction").permitAll()
//                                .requestMatchers("/").authenticated()
                                // hasRole("ADMIN")은 ROLE_ADMIN이 아닌 ADMIN만 써야함.
                                .requestMatchers("/admin").hasRole("ADMIN") // 권한이 없다면 403 포비든
                                // .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                                .anyRequest().authenticated()
                )

                // 로그인 페이지/액션 설정
                // 매개변수 타입 : FormLoginConfigurer<HttpSecurity>
                .formLogin((FormLoginConfigurer<HttpSecurity> login) -> login
                        .loginPage("/loginForm")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/loginAction") // 시큐리티가 자동처리
                        .defaultSuccessUrl("/")
                        // 로그인 성공 커스텀 핸들러
                        .successHandler((request, response, auth) -> {
                            System.out.println("로그인 성공했습니다.");
                            response.sendRedirect("/");
                        })
                        // 로그인 실패 에러 페이지
                        .failureUrl("/loginForm?error=error")
                        .permitAll()
                )

                // 로그아웃 URL/세션 설정
                .logout((LogoutConfigurer<HttpSecurity> logout) -> logout
                        // .logoutRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/logoutAction")) // 추천 안함
                        .logoutUrl("/logoutAction") // post방식 추천
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true) // 세션 객체 해제
                        .deleteCookies("JSESSIONID") // 쿠기 삭제

                )

                .oauth2Login((OAuth2LoginConfigurer<HttpSecurity> oauth) -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(successHandler())
                        .failureHandler(failureHandler())
                );

        return http.build();
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        // URL은 SnsController가 처리한다.
        return new SimpleUrlAuthenticationSuccessHandler("/snsLoginSuccess");
    }

    @Bean
    SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/snsLoginFailure");
    }
}