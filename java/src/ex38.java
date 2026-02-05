// 추상화 클래스와 인터페이스의 차이
// 추상화 클래스(일반클래스)는 다중상속이 안됨. 다단계 상속은 가능
class A {
}
class B extends A {
}
class C extends B {
}

interface IA {
}
interface IB {
}
interface IC {
}
class ID implements IA, IB, IC { // 다중구현(상속) 가능하다
}

// 추상화 클래스 1개, 인터페이스 다중 구현
class SupermanClass extends C implements IA,IB{
}

public class ex38 {
    public static void main(String[] args) {

    }

}
