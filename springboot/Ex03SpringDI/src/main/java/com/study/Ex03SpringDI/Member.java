package com.study.Ex03SpringDI;

// 회원정보를 담는 클래스
// POJO : 순수한 자바 클래스에 getter/setter만 있는 클래스
//      : 스프링이 관리하는 빈 클래스가 된다.
//      : vs EJB 클래스와는 다르게 단순하고 관리가 편하다.
//      : Plain Old Java Object의 약자.

import org.springframework.stereotype.Component;
@Component
// 스프링 빈으로 자동 생성됨. "member"라는 이름으로.
public class Member {
    private String name = "강감찬";

    // 기본 생성자
    // 스프링 프레임워크는 @Component가 붙은 클래스가 있으면,
    // 리플렉션 기술(클래스 정보를 파악해서 객체를 생성하는 기술)을 사용한다.
    // 리플렉션은 기본적으로 매개변수가 없는 생성자함수를 호출하도록 설계되어 있다.
    // 자바에 규칙에서 필드가 있는 생성자를 하나라도 직접 정의하면, 컴파일러가 자동으로
    // 만들어주던 기본 생성자를 더 이상 제공하지 않는다.
    // 이때, NotSuchMethodException이 발생한다.
    // -- 결론은 필드가 있는 생성자를 하나라도 만들면, @Component + 기본 생성자를 만들어줘야 한다. --

    public Member(){

    }

    // 필드가 있는 생성자
    public Member(String name){
        this.name = name;
    }

    // Getter/Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
