package com.study.Ex17JWT.controller;

import com.study.Ex17JWT.dto.UserDto;
import com.study.Ex17JWT.dto.UserRequestDto;
import com.study.Ex17JWT.entity.Users;
import com.study.Ex17JWT.service.impl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// JSON 문자열로 응답하는 컨트롤러 클래스
// REST API Server
@RestController  // @Controller + @ResponseBody를 합친 어노테이션
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class APIController {
    private final UsersServiceImpl usersService;

    @PostMapping("/signup")
    // HTTP 요청 데이터를 받는 방법 3가지
    // 1. @RequestParam : GET이나 POST에서 Param으로 보낸다.
    // 2. @RequestBody : body 데이터를 문자열 하나로 받는다. {id : ""}
    // 3. @ModelAttribute
    // * 경로 문구로 데이터를 받는 방법 : @PathVariable

    // HTTP 응답 데이터를 보내는 방법 3가지
    // 1. html 파일을 보낸다. ViewResolver, 동적 UI템플릿(타임리프, JSP)
    // 2. @ResponseBody : 문자열로 보내기 ("{"id","hong"}")
    // 3.                 클래스/맵 객체로 반환 -> JSON 문자열로 변환됨.
    // 4.                 JS로 보낸다. "<script></script>"
    // 5. redirect:/
    public UserDto createUser(@ModelAttribute UserRequestDto dto) {
        System.out.println(dto.getEmail());

        // DB에 저장하기
        return usersService.createUser(dto);
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute UserRequestDto dto) {
//        // DB에 있는 회원정보를 조회해서, 아이디/비번이 맞는지 확인한다.
//        // 아이디/비번이 맞으면, JWT토큰을 발행해준다.
//        try {
//            UserDto foundDto = usersService.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage()); // 이메일,암호에 맞는 회원이 없습니다.
//        }
//        return "JWT_TOKEN_Q312432DS4A";
//    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserRequestDto dto) {
        // DB에 있는 회원정보를 조회해서, 아이디/비번이 맞는지 확인한다.
        // 아이디/비번이 맞으면, JWT토큰을 발행해준다.

        // test@naver.com 1234 ADMIN
        UserDto foundDto = usersService.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

        // 가입된 회원임을 인증함. JWT 토큰을 발행하면 된다.
        return "JWT_TOKEN_Q312432DS4A";
    }
}
