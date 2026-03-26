package com.study.LibraryStie.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

// 일반 로그인 회원 엔티티
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
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "userRole", nullable = false)
    private String userRole;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "joinDate", nullable = false)
    private LocalDate joinDate;

    public void update(String password, String userName, String email, String phone) {
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }
}
