package com.study.Ex17JWT.entity;

import com.study.Ex17JWT.enumration.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users_jwt")
@Getter
//                @Transactional를 사용할 때
// @Setter : 엔티티 객체를 생성해서 값을 set하면 자동으로 db에 반영되서 데이터 변경이 일어날 수 있음.
@NoArgsConstructor(access = AccessLevel.PROTECTED)

// UserDetails : 시큐리티에서 인증에 관련된 사용자 정보를 담는 표준 계약(양식)
//               스프링 시큐리티에서 사용자의 정보를 담는 인터페이스(정보 객체)

// 인터페이스 : 가상함수(추상화 메소드)만 있는 클래스
// 추상화메소드 : 메소드의 선언만 있고, 코드 로직은 없는 메소드. 구현은 상속받은 자식클래스가 한다.
public class Users implements UserDetails {
    // 변수/상수
    // 배열

    // 자바 컬렉션 프레임워크
    // 1. List : 순차적인 데이터를 처리하기 위한 데이터 구조. 인덱스 있음.
    //        배열과 다른 점은? 중간에 삽입/수정/삭제가 용이함.
    // 2. Map : 인덱스 없음. KV구조로 구성된 데이터 구조. JSON/XML(통신,JS객체)
    //        스프링에서는 Map보다 클래스 객체로 많이 바인딩한다. 변수 이름을 key로, 변수 안의 값을 value로 쓴다.
    // 3. Set : 인덱스 없음. 중복된 값을 허용하지 않는다. DB쪽에서 unique(키) 속성.

    // 인터페이스 구현
    @Override
    //               인터페이스를 상속하는 모호한 객체라고 선언
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 계정의 권한 목록을 리턴한다.
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(userRole.getValue()));
        return roles;
    }

    @Override
    public String getUsername() {
        return this.email; // 계정의 고유한 아이디 리턴
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정의 만료 여부  true : 사용가능
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정의 잠김 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호의 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 활성화 여부
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY이 enum임.
    @Column(name = "id", nullable = false, unique = true) // 널 값 허용, 중복 값 허용 안함.
    // 가급적 필요한 데이터만 오픈한다.
    // 접근제한자 : private을 기본으로 하자. 자바의 철학(캡슐화, 은닉)
    // C/C++에서 접근 제한 없이 코드를 작성하면, 유지/관리/보수가 어려움.
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // enum 필드 만들 때 쓰는 어노테이션, EnumType.STRING : 문자열 타입
    @Column(name = "user_role", nullable = false)
    private UserRole userRole; // 'ROLE_USAR' 문자열은 문법체크가 안됨. 그래서 enum은 문법체크가 가능해서 많이들 씀.
    // enum을 사용하는 이유
    // 1. 가독성이 좋다.
    // 2. 컴파일러가 문법체크가 가능하다.

    @Builder
    public Users(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }
}
