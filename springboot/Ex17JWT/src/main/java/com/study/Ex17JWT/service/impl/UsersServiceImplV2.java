package com.study.Ex17JWT.service.impl;

import com.study.Ex17JWT.dto.UserDto;
import com.study.Ex17JWT.dto.UserRequestDto;
import com.study.Ex17JWT.service.UsersService;

import java.util.List;

//버전 2
public class UsersServiceImplV2  implements UsersService {
  @Override
  public UserDto createUser(UserRequestDto dto) {
    return null;
  }

  @Override
  public UserDto findUser(String email) {
    return null;
  }

  @Override
  public UserDto findByEmailAndPassword(String email, String password) {
    return null;
  }

  @Override
  public List<UserDto> findAll() {
    return List.of();
  }
}
