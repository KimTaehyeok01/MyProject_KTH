package com.study.Ex07Thymeleaf;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        model.addAttribute("address","서울");
        model.addAttribute("address_empty","");
        return "index2";
    }
    @RequestMapping(value = "/index3", method = RequestMethod.GET)
    public String index3(Model model){
        model.addAttribute("standardDate", new Date()) ;
        model.addAttribute("localDate", LocalDate.now());
        model.addAttribute("localDateTime", LocalDateTime.now());

        model.addAttribute("number1", 12345678);
        model.addAttribute("number2", 123456.789);
        return "index3";
    }

    @RequestMapping(value = "/index4", method = RequestMethod.GET)
    public String index4(Model model){
        // role : 역할 admin, member, guest -> 스프링 시큐리티(보안)
        model.addAttribute("role", "admin");
        return "index4";
    }

    @RequestMapping(value = "/index5", method = RequestMethod.GET)
    public String index5(Model model){
        Member member = new Member("Hong", "1234");
        model.addAttribute("member", member);

        List<Member> list = new ArrayList<>();
        list.add(new Member("lee", "1111"));
        list.add(new Member("hana", "2222"));
        list.add(new Member("tom", "3333"));
        model.addAttribute("list", list);
        return "index5";
    }

    @RequestMapping(value = "/index6", method = RequestMethod.GET)
    public String index6(Model model){
        return "index6";
    }

    @RequestMapping(value = "/index7", method = RequestMethod.GET)
    public String index7(Model model){
        return "index7";
    }

}
