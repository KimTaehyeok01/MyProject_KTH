package com.study.Ex14RestAPI;

import lombok.Builder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController // @ResponseBody가 포함되어 있어 클래스 내부 메서드에 따로 안 써도 됨!
@RequestMapping("/api/v1")
@Builder
public class ApiController {

    @GetMapping("/hello") // 조회는 명확하게 GetMapping으로!
    public String hello() {
        return "Hello, 저는 API 서버입니다.";
    }

    @PostMapping("/login")
    @ResponseBody // 응답을 http body에 실어보낸다.
    // @RequestBody : 요청 http body의 데이터를 매핑한다.

    // 1. 리턴 타입을 String이 아니라 Map이나 DTO로 잡는다.
    public Map<String, String> login(@RequestBody String body) {
        // 2. 받은 데이터 확인 (콘솔 로그)
        System.out.println("body = " + body);
        
        // 3. 응답 데이터를 담을 바구니(Map) 만들기
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "로그인되었습니다");

        // 4. Map을 통째로 리턴하면 스프링이 알아서 JSON 글자로 바꿔서 배달
        return response;
    }

    @PostMapping("/loginDto")
    @ResponseBody
    public ResDto loginDto(@RequestBody ReqDto reqDto){
        System.out.println(reqDto.getUsername());

        ResDto resDto = new ResDto();
        resDto.setStatus("success");
        resDto.setMessage("로그인 성공!");
        return resDto;
    }
}










