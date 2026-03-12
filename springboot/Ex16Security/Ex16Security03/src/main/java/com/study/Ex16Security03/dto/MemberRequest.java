package com.study.Ex16Security03.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class MemberRequest {
    private Long id;
    private String  username;
    private String password;
    private String nickName;
    private String userRole;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    @Builder
    public MemberRequest(Long id, String username, String password, String nickName,
                         String userRole, LocalDate joinDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.userRole = userRole;
        this.joinDate = joinDate;
    }
}
