package com.study.Ex04Autowired;

import org.springframework.stereotype.Component;

import javax.smartcardio.Card;

@Component("member") // 빈 이름을 직접 지정 가능
public class Member {
    private String name;
    private ICard iCard; // 확장성을 고려한 다형성 설계

    // 생성자 : Java 컴파일러는 기본생성자 자동 생성
    //       : Spring은 기본생성자 자동생성 안함.
    // 팁 : 빈으로 만들 클래스의 기본 생성자를 기본으로 만들면 된다.

    public Member() { // 기본으로 만들어 놓자.
    }

    public Member(String name, ICard iCard) {
        this.name = name;
        this.iCard = iCard;
    }

    // getter/setter

    public ICard getiCard() {
        return iCard;
    }

    public void setiCard(ICard iCard) {
        this.iCard = iCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
