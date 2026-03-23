package com.study.Ex17JWT.entity;

import com.study.Ex17JWT.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/*
create table users_jwt(
	id          BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	email 		VARCHAR(255) NOT NULL,
	password    VARCHAR(255) NOT NULL,
    user_role   VARCHAR(255) DEFAULT 'ROLE_USER'
);
 */
@Entity
@Table(name = "users_jwt")
@Getter
//                   @Transactional 를 사용할때
// @Setter : 엔티티 객체를 생성해서 값을 set하면 자동으로 DB쓰여진다.
//                   this.id = 10;
@NoArgsConstructor
// UserDetails : 시큐리티에서 인증에 관련된 사용자 정보를 다는 표준 계약(Contract, 양식)
// 인터페이스 : 가상함수(추상화메소드)만 있는 클래스
// 추상화 메소드 : 메소드의 선언만 있고, 코드 본체가 없는 메소드. 구현은 자식클래스에서 한다.
public class Users implements UserDetails {
  //접근제한자 : private 기본으로 하자. 자바의 철학(캡슐화, 은닉)
  //      가급적 필요한 데이터만 오픈한다. 탑골공원-잠잔다, 안전한곳-잠잔다.
  //      C/C++에서 접근제한없이 코드를 작성하면, 유지/관리/보수가 어려움.
  //      main() -> 5000줄
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  Long  id;
  @Column(nullable = false, unique = true) //널값 허용안함, 중복값 허용안함.
  private String email;
  @Column(nullable = false)
  private String password;
  @Enumerated(EnumType.STRING)
  private UserRole user_role;  // 'ROLE_USAR'  문자열은 문법체크가 안됨.
  //enum 사용하는 이유
  //1. 가독성이 좋다.  user_role = 0   //0 USER, 1 ADMIN
  //2. 컴파일러가 문법체크가 가능하다.
  @Builder //클래스안에서 선언하면, 일부 필드만 설정한다.
  public Users(String email, String password, UserRole userRole) {
      this.email = email;
      this.password = password;
      this.user_role = userRole;
  }
  // 변수/상수
  // 배열
  //자바 콜렉션 프레임워크
  //1. List(리스트) : 순차적인 데이터를 처리하기 위한 데이터 구조. 인덱스 있음.
  //           배열과 다른점? 삽입/수정/삭제 편하다.
  //2. Map(맵) : 순서 없음. Key-Value로 구성된 데이터 구조. JSON/XML(통신,JS객체)
  //   스프링에서는 클래스객체로 많이 바인딩한다. 변수이름은 키로, 변수안의 값은 밸류로 쓰인다.
  //3. Set(집합) : 순서 없음. 중복된 값을 허용하지 않는 데이터구조. DB쪽에서 Unique(키) 속성.

  //인터페이스 구현
  @Override
  //                          인터페이스를 상속하는 어떤 구현객체라고 선언
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //계정의 권한 목록을 리턴함
    Set<GrantedAuthority> roles = new HashSet<>();
    roles.add(new SimpleGrantedAuthority(user_role.getValue()));
    return roles;
  }

  @Override
  public String getUsername() {
    return this.email;  //계정의 고유한 아이디 리턴
  }

  @Override
  public boolean isAccountNonExpired() {
    return true; //계정의 만료여부   true: 사용가능
  }
  @Override
  public boolean isAccountNonLocked() {
    return true; //계정의 잠김 여부
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return true; //비밀번호의 만료 여부
  }
  @Override
  public boolean isEnabled() {
    return true; //활성화 여부
  }
}


