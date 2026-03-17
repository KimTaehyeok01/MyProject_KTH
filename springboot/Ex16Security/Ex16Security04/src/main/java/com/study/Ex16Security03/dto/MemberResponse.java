package com.study.Ex16Security03.dto;

import com.study.Ex16Security03.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponse {
    private Long id;
    private String username;
    private String password;
    private String nickName;
    private String userRole;
    private LocalDate joinDate;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.nickName = member.getNickName();
        this.userRole = member.getUserRole();
        this.joinDate = member.getJoinDate();
    }
}
