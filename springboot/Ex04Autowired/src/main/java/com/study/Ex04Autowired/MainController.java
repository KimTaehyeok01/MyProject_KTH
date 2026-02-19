package com.study.Ex04Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping
    @ResponseBody
    public String main() {
        return "스트링부트에 오신걸 환영합니다.";
    }

    // 필드 주입으로 객체 사용하기
    @Autowired
    private Member member1;

    @GetMapping("/member1")
    @ResponseBody
    public String member() {
        member1.setName("이순신");
        System.out.println(member1.getName());
        return member1.getName();
    }

    @Autowired
    @Qualifier("bCCard")
    ICard iCard; // 오토와이어링 할 수 없다.
    // ICard 구현 객체가 2개 이므로 선택을 해야 됨.
    // 선택하는 방법 : @Qualifier @Primary -> 여기서 우선순위는  @Qualifier > @Primary

    @GetMapping("/card")
    @ResponseBody
    public String card() {
        member1.setName("홍길동");
        member1.setiCard(iCard);
        member1.getiCard().buy("핸드폰");
        return "card() 호출됨";
    }
}















