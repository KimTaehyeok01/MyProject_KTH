package com.study.LibraryStie.dto;

import com.study.LibraryStie.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class MemberResponse {

    private Long id;
    private String userId;
    private String userName;
    private String email;
    private String phone;
    private String userRole;
    private LocalDate joinDate;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
        this.userName = member.getUserName();
        this.email = member.getEmail();
        this.phone = member.getPhone();
        this.userRole = member.getUserRole();
        this.joinDate = member.getJoinDate();
    }
}
