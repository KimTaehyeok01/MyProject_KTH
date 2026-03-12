package com.study.Ex16Security03.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "member_security")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "user_role", nullable = false)
    private String userRole;

    // @DateTimeFormat : HTML폼에서 전송된 날짜 문자열을
    // Java 날짜 객체로 변환해주는 어노테이션
    // ORM(Object Relation Mapping) : JPA에서 자바 객체와 DB테이블 간의 매핑
    // 매핑(Mapping) : HTML폼 문자열과 Java 객체 간의 변환
    // 그런데 엔티티를 HTML폼 매핑에 직접 사용하지 않는다. DTO에 사용하는게 좋다.
    // 엔티티 객체를 잘못 사용하면, 테이블에 직접 write될 여지가 있다.
    // @Transactional 서비스 클래스에서 setter함수 사용시 바로 db에 적용됨.
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate;

//    @Builder
//    public Member(Long id, ...){
//
//    }
}
