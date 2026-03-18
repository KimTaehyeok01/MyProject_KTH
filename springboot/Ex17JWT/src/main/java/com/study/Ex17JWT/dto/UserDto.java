package com.study.Ex17JWT.dto;

import com.study.Ex17JWT.enumration.UserRole;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private UserRole userRole;

}
