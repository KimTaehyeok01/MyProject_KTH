package com.example.Ex14LoginJoinDB.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Table(name = "member")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no", nullable = false)
    private Integer memberNo;

    @Column(name = "member_username", nullable = false)
    private String memberUserName;

    @Column(name = "member_password", nullable = false)
    private String memberPassword;

    @Column(name = "member_email", nullable = false)
    private String memberEmail;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "member_joindate", columnDefinition = "DATE", nullable = false)
    private LocalDate memberJoinDate;

    @Column(name = "member_role", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'ROLE_USER'")
    private String memberRole;

    @Builder
    public MemberEntity(Integer memberNo, String memberUserName, String memberPassword, String memberEmail, LocalDate memberJoinDate, String memberRole) {
        this.memberNo = memberNo;
        this.memberUserName = memberUserName;
        this.memberPassword = memberPassword;
        this.memberEmail = memberEmail;
        this.memberJoinDate = memberJoinDate;
        this.memberRole = memberRole;
    }

    public void update(String memberUserName, String memberPassword, String memberEmail, String memberRole) {
        this.memberUserName = memberUserName;
        this.memberPassword = memberPassword;
        this.memberEmail = memberEmail;
        this.memberRole = memberRole;
    }
}
