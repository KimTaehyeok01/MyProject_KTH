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
                .csrf((CsrfConfigurer<HttpSecurity> csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))

                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/", "/login", "/loginAction", "/signup", "/signupAction").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        .requestMatchers("/api/books/**").permitAll()        // 도서 조회 API
                        .requestMatchers("/snsLoginSuccess", "/snsLoginFailure").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .formLogin((FormLoginConfigurer<HttpSecurity> login) -> login
                        .loginPage("/login")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/loginAction")
                        .successHandler(customSuccessHandler)
                        .failureUrl("/login?error=error")
                        .permitAll()
                )

                .logout((LogoutConfigurer<HttpSecurity> logout) -> logout
                        .logoutUrl("/logoutAction")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

                .oauth2Login((OAuth2LoginConfigurer<HttpSecurity> oauth) -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(snsSuccessHandler())
                        .failureHandler(snsFailureHandler())
                )

                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // SNS 로그인 성공 시
    @Bean
    SimpleUrlAuthenticationSuccessHandler snsSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/snsLoginSuccess");
    }

    // SNS 로그인 실패 시
    @Bean
    SimpleUrlAuthenticationFailureHandler snsFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/snsLoginFailure");
    }
}
