package org.example;
// 객체지향 프로그래밍(OOP, Objective Oriented, Programming)
//     : Java언어의 설계 철학이 곧 OOP다.
//     : 객체(사물, 물건, 것) 지향(향하여 추구한다) 프로그래밍.
//     : 모든 사물을 객체로 추상화(모델링)하여 프로그래밍하는 기법.
//     : 클래스 = 객체 = 함수 + 변수 = 헹동 + 속성
// 예) 자동차를 클래스로 만들어보자
//     속성과 행동으로 구분한다.
//     변수(필드),     행동(함수 메서드)
//     가격 = 1000원, 행동 = 달린다

// 클래스 선언(클래스이름의 첫글자는 대문자)
class Car {
    // 속성
    int price = 1000;


    // 행동
    void run() {
        System.out.println("차가 달린다.");
    }
}

public class ex18 {
    public static void main(String[] args) {
        // 클래스로 부터 객체를 생성
        //클래스이름(타입) 객체이름 = new 클래스이름  ==  생성자함수();
        Car car = new Car();
        // 객체(클래스) 안의 변수(멤버변수)에 접근하려면, 점을 찍는다.
        car.run();
        System.out.println(car.price);

    }
}
