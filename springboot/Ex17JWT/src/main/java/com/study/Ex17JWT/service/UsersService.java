package com.study.Ex17JWT.service;

import com.study.Ex17JWT.dto.UserDto;
import com.study.Ex17JWT.dto.UserRequestDto;

import java.util.List;

//              추상화 클래스 vs 인터페이스
//공통점 : 가상함수(추상화 메소드)를 사용한다.
//            왜 사용? 설계와 구현의 관점.
//                     아키텍쳐 엔지니어, 코더         => 품질테스트(보안)
//                     SW뼈대(구조설계)  기능구현
// 업데이트 : USB-C 타입 - MP3
//                                     - Phone
//                                      - 보조밧데리
//                                      - 확장되는 기기
// * 기존 구현클래스를 건드리지 않고, 구현 클래스를 하나 더 만들어서 기능확장.
//@Service
public interface UsersService { //인터페이스로 규격(구현리스트)을 만듦.
  UserDto createUser(UserRequestDto dto); //회원가입
  UserDto findUser(String email) throws Exception;
  // throws Exception : 예외를 던진다. 왜?
  // 예외처리하는 방법
  // 1. try catch로 내가 직접(클래스) 예외 처리
  // 2. 예외 처리를 나를 호출한 메소드에게 넘긴다. 귀찮으니까.
  UserDto findByEmailAndPassword(String email, String password) throws Exception;
  List<UserDto> findAll();
}
