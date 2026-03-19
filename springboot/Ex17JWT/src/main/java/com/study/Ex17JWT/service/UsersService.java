package com.study.Ex17JWT.service;

import com.study.Ex17JWT.dto.UserDto;
import com.study.Ex17JWT.dto.UserRequestDto;

import java.util.List;

// 추상화 클래스 vs 인터페이스
// 추상화 클래스 : 추상화 메소드가 1개 이상 있는 클래스. 공통 기능을 상속하기 위해 사용됨.
// 인터페이스 : 추상화 메소드만 존재하는 클래스. 기능의 표준이나 계약을 정의함.

// 공통점 : 추상화메소드(가상함수)를 사용한다.
// 사용이유 : 설계와 구현의 관점
//               아키텍쳐 엔지이너, 코더 => 품질테스트(보안)
//               SW뼈대(구조설계)  기능구현
// 업데이트 : USB-C타입 - MP3
//                    - Phone, 보조배터리
// 기존 구현 클래스를 건들이지 않고, 구현 클래스를 하나 더 만들어서 기능 확장.

public interface UsersService { // 인터페이스로 구격(구현리스트)

    // 추상화 메소드
    UserDto createUser(UserRequestDto dto); // 회원가입

    UserDto findUser(String email);

    // throws Exception : 예외를 던진다. 왜?
    // 1. try catch로 내가 직접(클래스) 예외처리
    // 2. 예외처리를 나를 호출한 메소드에게 넘긴다. 내가 하기 귀찮으니까요.
    UserDto findByEmailAndPassword(String email, String password);

    List<UserDto> findAll();
}
