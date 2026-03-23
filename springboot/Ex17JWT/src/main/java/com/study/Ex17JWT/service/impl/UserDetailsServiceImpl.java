package com.study.Ex17JWT.service.impl;

import com.study.Ex17JWT.entity.Users;
import com.study.Ex17JWT.entity.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
//로그인 시 DB에서 사용자를 찾아서 Spring Security에게 건네주는 역할
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsersRepo userRepository;

    @Override
    public Users loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<Users> optional = userRepository.findByEmail(userEmail);
        if( optional.isEmpty() ){
            //예외를 강제 발생시킴.
            throw  new UsernameNotFoundException("이메일에 맞는 회원이 없습니다.");
        }
        Users entity = optional.get();

        return entity;
    }
}
