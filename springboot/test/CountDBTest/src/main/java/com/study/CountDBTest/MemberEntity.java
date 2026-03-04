package com.study.CountDBTest;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Table(name = "member")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name="user_id", nullable = false)
    private String userId;
    @Column(name="user_pw", nullable = false)
    private String userPw;
    @Column(name="user_name", nullable = false)
    private String userName;
    @Column(name="user_role", nullable = false)
    private String userRole;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;


    public MemberDto toSaveDto(){
        return MemberDto.builder()
                .id(id)
                .userId(userId)
                .userPw(userPw)
                .userName(userName)
                .userRole(userRole)
                .build();
    }

}











