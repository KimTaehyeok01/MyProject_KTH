package com.example.Ex14LoginJoinDB.Dto;

import com.example.Ex14LoginJoinDB.Entity.MemberEntity;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {
    private Integer memberNo;
    private String memberUserName;
    private String memberPassword;
    private String memberEmail;
    private LocalDate memberJoinDate;
    private String memberRole;

    // Entity -> Dto
    public MemberResponseDto(MemberEntity entity) {
        this.memberNo = entity.getMemberNo();
        this.memberUserName = entity.getMemberUserName();
        this.memberPassword = entity.getMemberPassword();
        this.memberEmail = entity.getMemberEmail();
        this.memberJoinDate = entity.getMemberJoinDate();
        this.memberRole = entity.getMemberRole();
    }
}
