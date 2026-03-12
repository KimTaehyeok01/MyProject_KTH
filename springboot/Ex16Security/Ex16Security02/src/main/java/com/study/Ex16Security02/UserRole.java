package com.study.Ex16Security02;

import lombok.Getter;

// 시큐리티의 인증(Authentication), 인가(Authorization)
// 1. 의미         신원확인               권한부여
// 2. 우선순위      가장먼저               인증 완료 후 수행
// 3. 실패시 응답   401 Unauthorized      403 Forbidden
// 4. 주요데이터    ID/PW, JWT토큰, 세션    ROLE(역할), Scopes(범위)

// enum : 열거형 - 상수나 문자열을 열거하여, 가독성있게 만드는 역할
// 열거형은 퍼블릭 접근제한자를 쓸 수 없다.
// 예) 0 : 유저 => UserRole.USER
//     1 : 관리자 => UserRole.ADMIN

@Getter
public enum UserRole {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String value;

    // 생성자 접근제한자 아래 타입은 private
    // enum생성자 : public 안씀, 각 상수 선언시 자동호출됨.
    // UserRole role = UserRole.USER; -> 상수 선언
    UserRole(String value){
        this.value = value;
    }
}
