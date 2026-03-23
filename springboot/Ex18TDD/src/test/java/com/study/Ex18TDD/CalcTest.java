package com.study.Ex18TDD;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalcTest {
    static Calc calc = null;

    @BeforeAll // 테스트 케이스 실행 전에 한 번 호출됨.
    static void init(){
        System.out.println("init() 실행됨");
        calc = new Calc();
    }

    @Test
    // @DisplayName : 테스트 메소드 이름 변경
    @DisplayName("add 함수 테스트")
    void add() {
        assertEquals(10, calc.add(8,2));
    }

    @Test
    @DisplayName("sub 함수 테스트")
    void sub() {
        assertEquals(8, calc.sub(10,2));
    }

    @Test
    @DisplayName("mul 함수 테스트")
    void mul() {
        assertEquals(8, calc.mul(4,2));
    }

    @Test
    @DisplayName("dev 함수 테스트")
    void dev() {
        assertEquals(8, calc.dev(16,2));
    }
}