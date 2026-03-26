package com.study.LibraryStie.dto;

import com.study.LibraryStie.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// 회원 정보 응답 DTO
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

    // Member 엔티티를 DTO로 변환하는 생성자
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
