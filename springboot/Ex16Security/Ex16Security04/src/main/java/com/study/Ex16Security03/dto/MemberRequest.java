package com.study.Ex16Security03.dto;

import com.study.Ex16Security03.entity.Member;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor // Java 컴파일러에서 자동 생성하나, 스프링 빈 생성시에는 자동생성 하지 않는다.
@AllArgsConstructor
@Builder
public class MemberRequest { // DTO <-> Entity
    private Long id;

    @NotBlank(message = "아이디를 입력하세요")
    @Size(min = 4, max = 20)
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 4, max = 20)
    private String password;

    @NotBlank(message = "이름을 입력하세요")
    @Size(max = 20)
    private String nickName;

    @NotBlank(message = "권한을 선택하세요")
    private String userRole;

    @NotNull(message = "가입일자를 입력하세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    public Member toSaveEntity(){
        // 빌더 패턴을 왜 사용? 생성자 함수를 편하게 쓰고자
        // 1. new Member(id, username, ....); : 매개변수 개수와 순서가 동일해야함.
        return Member.builder()
                .username(username)
                .password(password)
                .nickName(nickName)
                .userRole(userRole)
                .joinDate(LocalDate.now())
                .build();
    }

    public Member toUpdateEntity(){
        return Member.builder()
                .id(id)
                .username(username)
                .password(password)
                .nickName(nickName)
                .userRole(userRole)
                .joinDate(LocalDate.now())
                .build();
    }
}
