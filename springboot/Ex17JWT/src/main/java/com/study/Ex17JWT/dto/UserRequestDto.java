package com.study.Ex17JWT.dto;

import com.study.Ex17JWT.enumration.UserRole;
import lombok.*;

// signUp, login, 데이터 바인딩 용도
@Getter @Setter
public class UserRequestDto {
    private String email;
    private String password;
    private UserRole userRole;

}
