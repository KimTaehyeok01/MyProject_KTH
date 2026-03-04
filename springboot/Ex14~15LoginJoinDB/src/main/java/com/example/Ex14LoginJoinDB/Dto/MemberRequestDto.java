package com.example.Ex14LoginJoinDB.Dto;

import com.example.Ex14LoginJoinDB.Entity.MemberEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestDto {
    private Integer memberNo;
    private String memberUserName;
    private String memberPassword;
    private String memberEmail;
    private LocalDate memberJoinDate;
    private String memberRole;

    @Builder
    public MemberRequestDto(Integer memberNo, String memberUserName, String memberPassword, String memberEmail, LocalDate memberJoinDate, String memberRole) {
        this.memberNo = memberNo;
        this.memberUserName = memberUserName;
        this.memberPassword = memberPassword;
        this.memberEmail = memberEmail;
        this.memberJoinDate = memberJoinDate;
        this.memberRole = memberRole;
    }

    // Dto -> Entity
    public MemberEntity toSaveEntity() {
        return MemberEntity.builder()
                .memberNo(memberNo)
                .memberUserName(memberUserName)
                .memberPassword(memberPassword)
                .memberEmail(memberEmail)
                .memberJoinDate(LocalDate.now())
                .memberRole(memberRole)
                .build();
    }
}
