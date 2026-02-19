package com.study.Ex04Autowired;

// 신용카드 인터페이스
//    사용이유 : 확장성 - bc카드, 현대카드, 삼성카드...
//           : 설계와 구현 관점에서 업데이트 용이함.
public interface ICard {
    public void buy(String itemName); // 추상화 메소드

}
