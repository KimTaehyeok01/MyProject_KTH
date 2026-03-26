package com.study.LibraryStie.dto;

import com.study.LibraryStie.entity.member.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

// 회원가입 / 로그인 요청 DTO
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequest {

    private Long id;

    @NotBlank(message = "아이디를 입력하세요")
    @Size(min = 4, max = 20, message = "아이디는 4~20자 사이여야 합니다")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 4, max = 20, message = "비밀번호는 4~20자 사이여야 합니다")
    private String password;

    @NotBlank(message = "이름을 입력하세요")
    @Size(max = 20, message = "이름은 20자 이하여야 합니다")
    private String userName;

    @NotBlank(message = "이메일을 입력하세요")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    private String phone;

    // 회원가입 시 엔티티로 변환
    public Member toSaveEntity() {
        return Member.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .email(email)
                .phone(phone)
                .userRole("ROLE_USER")
                .joinDate(LocalDate.now())
                .build();
    }
}
