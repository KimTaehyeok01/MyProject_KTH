package com.study.Ex17JWT.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
//enum : 열거형,  숫자로 값을 정하면 기억하기 어렵기 때문에,
//           문자열 형태의 값으로 값을 정해서 가득성을 늘리는 것.
//          UserRole.USER
@Getter
@AllArgsConstructor
public enum UserRole {
  USER("ROLE_USER"),
  ADMIN("ROLE_ADMIN");

  private String value;
}
