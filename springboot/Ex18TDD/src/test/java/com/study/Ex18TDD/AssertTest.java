package com.study.Ex18TDD;
//단정(Assert) 메서드 : 테스트 케이스의 수행 결과를 판별하는 용도로 사용.
//종류
//assertArrayEquals(a, b) : 배열 a와 b가 일치함을 확인한다.
//assertEquals(a, b) : 객체 a와 b가 일치함을 확인한다. (객체에 정의되어 있는 equals를 통해 비교한다.)
//assertSame(a, b) : 객체 a와 b 가 같은 객체임을 확인 한다. (객체 자체를 비교한다. == )
//assertTrue(a) : 조건 a가 참인지를 확인한다.
//assertFalse(a) : 조건 a가 거짓인지를 확인한다.
//assertNotNull(a) : 객체 a가 null인지 확인한다.
//assertAll() : 모든 종류의 assert를 각각 다 실행한다. 중간에 멈추지 않음.

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// 클래스와 메소드의 기능을 단위 테스트 한다.
class Adder {
    public int add(int x, int y) {
        return x + y;
    }
}

public class AssertTest {
    private final String[] arr1 = {"a", "b"};
    private final String[] arr2 = {"a", "c"};
    private final Adder adder1 = new Adder();
    private final Adder adder2 = new Adder();

    // @Test : 테스트 케이스 함수라는 것을 알려주는 어노테이션
    // 테스트 케이스는 단독 실행이 가능함.
    @Test
    void test1() {
        System.out.println("테스트1 입니다.");
        // 두 개의 배열의 내용을 비교하고,
        // 내용이 다르면 assert단정(실행 중단)한다.
        assertArrayEquals(arr1, arr2, "배열 값이 서로 다릅니다.");
        System.out.println("테스트 완료");
    }

    @Test
    void test2() {
        System.out.println("테스트2 입니다.");
        assertEquals(3, adder1.add(1,2), "서로 값이 다릅니다.");
    }

    @Test
    void test3(){
        // assertSame : == 비교, 주소값(해쉬코드값)을 비교한다. 같은 객체인가?
        assertSame(adder1, adder1, "서로 다른 객체입니다.1");
        assertSame(adder1, adder2, "서로 다른 객체입니다.2");
    }

    @Test
    public void test4(){
        // 조건이 true면 통과, false면 assert
        assertTrue(3 < 10, "3은 10보다 작습니다.");

        // 조건이 false면 통과, true면 assert
        assertFalse(3 > 10, "3은 10보다 작습니다.");
    }

    @Test
    void test5(){
        // assertNull : null이면 통과, null이 아니면 중단(에러)
        assertNull(null, "객체가 null 아닙니다.");

        // assertNotNull : null이 아니면 통과, null이면 중단(에러)
        assertNotNull(adder2, "객체가 null입니다.");
    }

    @Test
    void test7(){
        assertAll(
                ()->{assertTrue(false, "조건이 거짓임.");},
                ()->{Object obj = new Object();
                assertNotNull(obj, "obj가 널임1");
                assertNotNull(null, "obj가 널임2");
                });
    }
}
