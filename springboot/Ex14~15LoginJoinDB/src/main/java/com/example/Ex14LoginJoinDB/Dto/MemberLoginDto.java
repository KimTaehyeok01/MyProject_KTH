package com.example.Ex14LoginJoinDB.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberLoginDto {
    @NotBlank(message = "아이디에 null이나 공백문자는 입력할 수 없습니다.")
    @Size(min = 4, max = 20)
    private String memberUserName;
    @NotBlank(message = "비밀번호에 null이나 공백문자는 입력할 수 없습니다.")
    @Size(min = 5, max = 20)
    private String memberPassword;
    private String memberRole;
}
