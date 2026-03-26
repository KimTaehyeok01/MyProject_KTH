package com.study.LibraryStie.domain.snsUser;

import com.study.LibraryStie.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "provider", nullable = false, length = 50)
    private String provider;

    @Column(name = "providerId", nullable = false, length = 100)
    private String providerId;

    @Column(name = "picture", nullable = false)
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "userRole", nullable = false)
    private UserRole userRole;

    @Column(name = "joinDate")
    private LocalDate joinDate;

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

    public SnsUser update(String name, String picture, String email) {
        this.name = name;
        this.picture = picture;
        this.email = email;
        return this;
    }

    public String getRoleKey() {
        return this.userRole.getValue();
    }
}
