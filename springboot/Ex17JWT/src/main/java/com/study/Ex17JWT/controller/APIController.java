package com.study.Ex17JWT.controller;

import com.study.Ex17JWT.config.JwtUtil;
import com.study.Ex17JWT.dto.UserDto;
import com.study.Ex17JWT.dto.UserRequestDto;
import com.study.Ex17JWT.service.impl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

// JSON문자열로 응답하는 컨트롤러 클래스
// REST API Server
@RestController  // @Controller + @ResponseBody
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ApiController {
  private final UsersServiceImpl usersService;
  private final JwtUtil jwtUtil;

  //HTTP 요청 데이터를 받는 방법 3가지
  //1. @RequestParam : GET/POST에서 Param으로 보냈을 때
  //2. @RequestBody : Body데이터를 문자열 한개로 받는다 "{ "id" : "hong" }"
  //3. @ModelAttribute : DTO 클래스/맵 바인딩
  // * 경로 문구로 데이터를 받는 방법 : @PathVariable
  //HTTP 응답 데이터를 보내는 방법 3가지
  //1. html 파일을 보낸다. ViewResolver, 동적 UI템플릿(타임리프,JSP)
  //2. @ResponseBody : 문자열로 보내기 ("{ "id", "hong" }" )
  //3.                               : 클래스/맵 객체로 반환 -> JSON 문자열로 변환됨.
  //4.                               : JS로 보낸다. "<script>alert; histroy.back()</script>"
  //5. "redirect:url"
  @PostMapping("/signup")
  public UserDto createUser(@ModelAttribute UserRequestDto dto) {
    System.out.println(dto.getEmail());

    //DB에 저장하기
    return usersService.createUser(dto);
  }

  @PostMapping("/login")
  public String login(@ModelAttribute UserRequestDto dto) {
    //DB에 있는 회원정보를 조회해서, 아이디/비번이 맞는지 확인한다.
    //아이디/비번이 맞으면, JWT토큰을 발행해 준다.
    // test@naver.com   1234  ADMIN
    UserDto foundDto = null;
    try {
      foundDto = usersService.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e.getMessage()); //"이메일,암호에 맞는 회원이 없습니다."
      return "이메일,암호에 맞는 회원이 없습니다.";
    }
    //가입된 회원임을 인증함. JWT토큰 발생하면 된다.

    return jwtUtil.createToken(foundDto.getEmail(),
        Arrays.asList(foundDto.getUserRole().getValue()));
  }

  @GetMapping("/mypage")
  // @Secured : 보안(인가) 등급 설정으로 USER와 ADMIN만 접근 가능함.
  @Secured( {"ROLE_USER", "ROLE_ADMIN"} )
  //시큐리티의 현재 인증 정보를 주입받는다.
  public UserDto mypage(Authentication authentication) throws Exception {
    if (authentication == null) {
      throw new BadCredentialsException("회원 인증정보를 찾을 수 없습니다.");
    }
    System.out.println(authentication.getName());
    return usersService.findUser(authentication.getName());
  }

  @GetMapping("/admin")
  @Secured( {"ROLE_ADMIN"} )
  public List<UserDto> admin(){
    return usersService.findAll();
  }

} //class
