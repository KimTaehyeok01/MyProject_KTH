package com.study.Ex17JWT.service.impl;

import com.study.Ex17JWT.dto.UserDto;
import com.study.Ex17JWT.dto.UserRequestDto;
import com.study.Ex17JWT.entity.Users;
import com.study.Ex17JWT.entity.UsersRepo;
import com.study.Ex17JWT.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
  //생성자 주입, final : 상수 선언 - 재할당 안됨. 안정성 올라간다.
  private final UsersRepo usersRepo;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional  //트랜잭션 처리 - 오류발생시 롤백(Rollback)한다.
  public UserDto createUser(UserRequestDto dto) { //회원가입
    //비밀번호 암호화 BCrypt 사용
    Users entity = Users.builder()
        .email(dto.getEmail())
        .password( passwordEncoder.encode( dto.getPassword() ) )
        .userRole(dto.getUserRole())
        .build();
    //트랜잭션 안에서 엔티티 객체가 생성된 상태에서
    //엔티티의 set함수를 호출하면, 자동으로 DB쓰여진다. 2번이상 동작하는 경우도 있다.
    //연관관계 매핑시 복수/잘못된 호출이 될 수 있다.
    //entity.setEmail("test@gmail.com"); //DB Commit됨!

    Users newEntity = usersRepo.save(entity);

    UserDto newDto = UserDto.builder()
        .id(newEntity.getId())
        .email(newEntity.getEmail())
        .password(newEntity.getPassword())
        .userRole(newEntity.getUser_role())
        .build();

    return newDto;
  }

  // 회원정보 단건 조회
  @Override
  @Transactional(readOnly = true)  //조회-Select만 한다면
  public UserDto findUser(String email) throws Exception {
    Optional<Users> optional = usersRepo.findByEmail(email);
    if( optional.isEmpty() ){
      //예외를 강제 발생시킴.
      throw  new Exception("이메일에 맞는 회원이 없습니다.");
    }
    Users entity = optional.get();
    return UserDto.builder()
        .email(entity.getEmail())
        .password(entity.getPassword())
        .userRole(entity.getUser_role())
        .build();
  }

  //아이디/비번으로 로그인 처리
  @Override
  @Transactional(readOnly = true)                                                            // 예외를 던진다.
  public UserDto findByEmailAndPassword(String email, String password)  throws Exception {
    Optional<Users> optional = usersRepo.findByEmail(email);
    if( optional.isEmpty() ){
      //예외를 강제 발생시킴.
      throw  new Exception("이메일에 맞는 회원이 없습니다.");
    }
    Users entity = optional.get();
    //DB에 있는 암호화된 비번과 유저입력한 암호가 같은지 확인한다.
    //BCrypt알고리즘은 복호화가 불가하다.
    //유저 입력 암호 -> 암호화해서 DB의 암호화 비번과 매칭(match)한다.
    if( passwordEncoder.matches(password, entity.getPassword()) == false ){
      throw  new Exception("비밀번호가 맞지 않습니다.");
    }

    return UserDto.builder()
        .email(entity.getEmail())
        .password(entity.getPassword())
        .userRole(entity.getUser_role())
        .build();
  }

  //전체 회원 목록
  @Override
  @Transactional(readOnly = true)
  public List<UserDto> findAll() {
    //Entity클래스 리스트를 DTO 클래스 리스트로 변환하는 stream()함수 이용.
    return usersRepo.findAll().stream().map(
        entity -> UserDto.builder()
            .id(entity.getId())
            .email(entity.getEmail())
            .password(entity.getPassword())
            .userRole(entity.getUser_role())
            .build()
    ).collect(Collectors.toList());
  }
}//class
