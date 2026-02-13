package com.study.Ex03SpringDI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// Controller : HTTP 요청을 가장 먼저 처리하는 컨트롤러 클래스이다.
//              GET/POST/PUT/PATCH/DELETE 메소드를 처리한다.
//              Read/Insert/UpdateAll/Update/Delete DB액션
// @Component : mainController 빈으로 만든다.

@Controller
public class MianController {
    // HTTP URL : "localhost:8080/"
    //  @GetMapping("/") or  @GetMapping이렇게 해도 됨. -> HTTP URL : "localhost:8080"
    @GetMapping("/") // "/"를 Root 경로라고 함. @GetMapping은 Get요청을 처리하는 메소드를 선언한다.
    @ResponseBody // 응답을 html파일로 하지않고, Body데이터(문자열)로 한다는 뜻.
    public String main(){
        return "스프링부트 웹서버가 준비되었습니다.";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        // URL : localhost:8080/test
        return "테스트 경로입니다.";
    }
}
