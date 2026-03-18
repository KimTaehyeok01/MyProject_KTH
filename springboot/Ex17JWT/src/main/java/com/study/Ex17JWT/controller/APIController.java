package com.study.Ex17JWT.controller;

import com.study.Ex17JWT.dto.UserDto;
import com.study.Ex17JWT.dto.UserRequestDto;
import com.study.Ex17JWT.enumration.UserRole;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// JSON 문자열로 응답하는 컨트롤러 클래스
// REST API Server
@RestController  // @Controller + @ResponseBody를 합친 어노테이션
@RequestMapping("/api/users")
public class APIController {

    @PostMapping("signup")
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
    public UserDto createUser(@ModelAttribute UserRequestDto dto){
        System.out.println(dto.getEmail());

        return UserDto.builder()
                .id(0L)
                .email("test1234@naver.com")
                .password("1234")
                .userRole(UserRole.ADMIN)
                .build();
    }
}
