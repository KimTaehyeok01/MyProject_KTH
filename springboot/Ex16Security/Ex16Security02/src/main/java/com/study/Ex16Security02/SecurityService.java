package com.study.Ex16Security02;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
// UserDetailsService 인터페이스를 상속받는 클래스
public class SecurityService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 아이디를 통해, 사용자 정보와 권환(ROLE)을 스프링 시큐리티에 전달해주는 코드
        List<GrantedAuthority> authorityList = new ArrayList<>();

        // 인가 리스트에서 추가
        // 테스트로 관리자 권한을 한개 추가해준다.
        authorityList.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue() ) );
        // 권한과 함께 사용자 정보 객체를 반환한다.
        // username : 아이디 - "admin"
        // password : 비밀번호 - "1234"

        // Given that there is no default password encoder configured
        // 패스워르를 엔코딩(암호)처리를 안하면 에러가 남. 암호화해서 넘겨야함.
        // BCrypt 사이트(bcrypt-generator.com)에서 암호를 생성해서 붙여넣는다.
        // $2a$12$0ygh9yEtqMZ6q7pyoQ4gPOOHOSR99ShInvsnPIThR9p.zwy4hWMnq
        System.out.println("시큐리티가 사용자 정보를 조회함.");
        return new User("admin", "$2a$12$0ygh9yEtqMZ6q7pyoQ4gPOOHOSR99ShInvsnPIThR9p.zwy4hWMnq",authorityList);
    }
}
