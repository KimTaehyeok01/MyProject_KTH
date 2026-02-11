// 람다식(Lambda Expression)
//    : JS의 화살표함수(함수형변수)와 역할이 유사함.
//    : JDK 1.8부터 지원.
//    : 원래 없던 기능인데, 함수형 언어를 지원하기 위해 만듦.
//    : 억지로 인터페이스 문법을 차용한다.

// 람다식 선언
@FunctionalInterface // 람다식 선언을 위한 어노테이션
interface MyFunc1 {
    // 단 하나의 메소드만 선언(익명함수라 오직 메소드가 하나여야 함)
    int sum(int x, int y); // 가상함수(추상메소드)
}

// 인터페이스 구현 클래스
class MyFunc1Impl implements MyFunc1{
    @Override
    public int sum(int x, int y) {
        return x+y;
    }
}

public class ex60 {
    public static void main(String[] args) {
        // 인터페이스 구현 객체 - 일반클래스
        MyFunc1Impl f2 = new MyFunc1Impl();
        System.out.println(f2.sum(10,20));

        // 인터페이스 구현 객체 - 익명함수(람다식 객체)
        MyFunc1 f1 = (int x, int y) -> {
            return x + y;
        };
        System.out.println(f1.sum(10, 20));

        execFunc(f1);
    }
    static void execFunc(MyFunc1 func1){
        System.out.println(func1.sum(30,40));
    }
}
