package com.study.Ex16Security03.service;

import com.study.Ex16Security03.entity.Member;
import com.study.Ex16Security03.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.study.Ex16Security03.enumeration.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Service
//// UserDetailsService 인터페이스를 상속받는 클래스
//public class SecurityService implements UserDetailsService {
//    @Autowired
//    private MemberRepository repository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 실제 DB에서 사용자 정보를 시큐리티에 넘겨준다.
//        Optional<Member> optional = repository.findByUserName(username);
//        if(optional.isEmpty()){
//            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
//        }
//
//        Member member = optional.get();
//
//        List<GrantedAuthority> authorityList = new ArrayList<>();
//        authorityList.add(new SimpleGrantedAuthority(member.getUserRole()));
//        System.out.println("시큐리타가 사용자의 정보를 조회함. " + member.getUsername());
//
//        return new User(member.getUsername(), member.getPassword(), authorityList);
//    }
//}

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService{
    private final MemberRepository repository;

    // 시큐리티에서 로그인에서 사용자 정보를 가져오는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = repository.findByUserName(username).orElseThrow(()->
                new UsernameNotFoundException("사용자를 찾을 수 업습니다."));

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(member.getUserRole()));
        System.out.println("사용자 정보를 조회함 " + member.getUsername());

        return new User(member.getUsername(), member.getPassword(), authorityList);
    }
}








