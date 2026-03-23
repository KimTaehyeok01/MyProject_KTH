package com.study.Ex17JWT.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// @Configuration : 스프링에서 환경설정 클래스로 등록한다.
// @Bean : 메소드 위에 위치하고, 반환객체를 빈으로 등록한다.
@Configuration
public class PasswordEncoderConfig {
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }
}

