package com.study.LibraryStie.entity.snsUser;

import com.study.LibraryStie.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// SNS 소셜 로그인 회원 엔티티 (Kakao, Naver)
@Entity
@Table(name = "sns_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;        // 닉네임

    @Column(name = "email", nullable = false)
    private String email;       // 이메일

    @Column(name = "provider", nullable = false, length = 50)
    private String provider;    // kakao, naver

    @Column(name = "providerId", nullable = false, length = 100)
    private String providerId;  // 각 플랫폼 고유 ID

    @Column(name = "picture", nullable = false)
    private String picture;     // 프로필 이미지 URL

    @Enumerated(EnumType.STRING)
    @Column(name = "userRole", nullable = false)
    private UserRole userRole;  // 권한 (USER, ADMIN)

    @Column(name = "joinDate")
    private LocalDate joinDate; // 가입일

    // 일부 필드만 받는 생성자 (빌더용)
    @Builder
    public SnsUser(String name, String email, String picture,
                   String provider, String providerId, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
        this.providerId = providerId;
        this.userRole = userRole;
        this.joinDate = LocalDate.now();
    }

    // 정보 업데이트 (이름/사진/이메일 변경)
    public SnsUser update(String name, String picture, String email) {
        this.name = name;
        this.picture = picture;
        this.email = email;
        return this;
    }

    // 권한 문자열 반환 (ex. "ROLE_USER")
    public String getRoleKey() {
        return this.userRole.getValue();
    }
}
