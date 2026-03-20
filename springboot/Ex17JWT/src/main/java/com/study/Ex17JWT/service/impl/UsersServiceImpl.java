package com.study.Ex17JWT.service.impl;

import com.study.Ex17JWT.dto.UserDto;
import com.study.Ex17JWT.dto.UserRequestDto;
import com.study.Ex17JWT.entity.Users;
import com.study.Ex17JWT.repository.UsersRepository;
import com.study.Ex17JWT.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    // final을 쓰는 이유 : 재할당을 막기 위해서. 불변
    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional // 트랜잭션 처리 - 오류 발생시 롤백(RollBack)한다.
    public UserDto createUser(UserRequestDto dto) {

        // 회원가입
        Users users = Users.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .userRole(dto.getUserRole())
                .build();
        Users newEntity = repository.save(users);

       return UserDto.builder()
                .id(newEntity.getId())
                .email(newEntity.getEmail())
                .password(newEntity.getPassword())
                .userRole(newEntity.getUserRole())
                .build();

        // 트랜젝션 안에서 엔티티 객체가 생성된 상태에서
        // 엔티티의 set함수를 호출하면 자동으로 db에 저장된다. 2번 이상 동작하는 경우도 있다.
        // 연관관계 매핑시 복수/잘못된 호출이 될 수도 있다.
        // users.setEmail("test@naber.com"); // DB commit됨.
    }

    // 회원정보 단건 조회
    @Override
    @Transactional(readOnly = true)
    public UserDto findUser(String email) {
        return null;
    }

    // 아이디/비번으로 로그인 처리
    @Override
    @Transactional(readOnly = true)
    //                                                                     예외를 던진다.
    public UserDto findByEmailAndPassword(String email, String password) {
        Users entity = repository.findByEmailAndPassword(email,password).orElseThrow(()->
                new IllegalArgumentException("사용자를 찾을 수가 없습니다."));

//        Optional<Users> optional = repository.findByEmailAndPassword(email,password);
//        if(optional.isEmpty()){
//            // 예외를 강제로 발생시킴.
//            throw new Exception("이메일, 암호에 맞는 회원이 없습니다.");
//        }
//        User entity = optional.get();

        return UserDto.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .userRole(entity.getUserRole())
                .build();
    }

    // 전체 회원 목록 조회
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return List.of();
    }
}