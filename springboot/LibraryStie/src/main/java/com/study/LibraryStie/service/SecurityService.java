package com.study.LibraryStie.service;

import com.study.LibraryStie.entity.member.Member;
import com.study.LibraryStie.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 일반 로그인 시 Spring Security 가 사용하는 인증 서비스
// loadUserByUsername() 이 호출되면 DB에서 회원을 조회해 UserDetails 로 반환
@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // DB에서 userId로 회원 조회
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다: " + userId));

        // Spring Security 가 인식할 수 있는 UserDetails 객체로 변환
        return User.builder()
                .username(member.getUserId())
                .password(member.getPassword())
                .roles(member.getUserRole().replace("ROLE_", "")) // "ROLE_USER" -> "USER"
                .build();
    }
}
