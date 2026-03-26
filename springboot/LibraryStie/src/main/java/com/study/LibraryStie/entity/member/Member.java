package com.study.LibraryStie.entity.member;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

// 일반 로그인 회원 엔티티 (form login)
@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "userId", nullable = false, unique = true)
    private String userId;      // 로그인 아이디

    @Column(name = "password", nullable = false)
    private String password;    // BCrypt 암호화된 비밀번호

    @Column(name = "userName", nullable = false)
    private String userName;    // 이름

    @Column(name = "email", nullable = false, unique = true)
    private String email;       // 이메일

    @Column(name = "phone")
    private String phone;       // 전화번호

    @Column(name = "userRole", nullable = false)
    private String userRole;    // 권한 (ROLE_USER, ROLE_ADMIN)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "joinDate", nullable = false)
    private LocalDate joinDate; // 가입일

    // 회원 정보 수정 메서드 (Dirty Checking)
    public void update(String password, String userName, String email, String phone) {
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }
}
