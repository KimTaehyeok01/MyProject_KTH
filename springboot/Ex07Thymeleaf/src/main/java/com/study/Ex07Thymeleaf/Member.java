package com.study.Ex07Thymeleaf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter/setter/RequiredArgsConstructor
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드가 있는 생성자
public class Member {
    private String username; // 스프링 시큐리티에서 사용자 계정을 의미함.
    private String password; // 스프링 시큐리티에서 사용자 암호를 의미함.
}
