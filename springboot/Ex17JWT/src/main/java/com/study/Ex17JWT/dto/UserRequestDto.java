package com.study.Ex17JWT.dto;

import com.study.Ex17JWT.enumeration.UserRole;
import lombok.Getter;
import lombok.Setter;

// signup, login 데이터 바인딩 용도
@Getter @Setter
public class UserRequestDto {
  private String email;
  private String password;
  private UserRole userRole;
}
