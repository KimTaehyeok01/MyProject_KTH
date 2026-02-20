package com.study.Ex07Thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @RequestMapping(value = "./", method = RequestMethod.GET)
    public String main(){
        return "index"; // 동적 웹에서는 파일 이름만 적으면 된다.(정적 웹에서는 확장자도 다 써줘야 한다. index.html)
    }

    @GetMapping("/index1")
    // Model클래스 : 스프링 MVC모델에서 데이터를 전달하는 용도의 클래스
    // 매개변수로 선언하면 스프링에 주입(new)이 됨
    //  내부적으로 Map(Key-Value)형태를 가지고 있다.
    // Model 객체에서 KV를 넣으면, 타임리프에서 가져다 사용한다.
    public String index1(Model model){
        model.addAttribute("name_text", "<ins>홍길동</ins>");
        model.addAttribute("name_html", "<ins>홍길동</ins>");
        model.addAttribute("server_value","HONG");
        return "index";
    }

    @RequestMapping(value = "/index2", method = RequestMethod.GET)
    public String index2(Model model){
        model.addAttribute("address_null", null);
        return "index2";
    }

    @GetMapping("index3")
    public String index3(Model model){
        model.addAttribute("name_test","김태혁");
        model.addAttribute("name_test","<ins>김태혁</ins>");
        model.addAttribute("age_test", 29);
        model.addAttribute("address_null", null);
        return "index3";
    }
}
