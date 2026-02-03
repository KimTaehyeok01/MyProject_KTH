public class ex23 {

    // 메소드는 메소드 밖에서 선언해야 함.

    static void echo(){
        System.out.println("echo");
    }

    static void echo(String str){
        System.out.println("echo" + str);
    }

    static void echo(int i){
        System.out.println("echo" + i);
    }

    static void echo(int i, int j){
        System.out.println("echo" + i+j);
    }

    public static void main(String[] args) {

        echo();
        echo("야호~");
        echo(10);
        echo(10,20);

        // 메소드 오버로딩
        // 과적(적정한 부하 이상으로 짐을 실는 것)
        // : 매개변수의 타입과 갯수를 다르게 하여
        //   함수의 기능을 확장하는 것
        // 사용하는 이유 : 같은 이름의 함수를 여러 번 쓸수 있다.
        // 사용 예
        System.out.println(10);
        System.out.println("문자열");

        // 메소드 오버라이딩
        // : 상속 관계에서 자식클래스의 메소드가
        //   부모클래스의 메소드를 재정의 하는 것.

        // 자바 기술면접
        // 1. 오버로딩 vs 오버라이딩 공통점과 차이점
        // 2. 추상화 클래스 vs 인터페이스 공통점과 차이점
        // 3. 다형성
        // 4. 상속
        // 5. 생성자 함수
        // 6. 객체지향프로그래밍이란?
        // 7. 프로젝트 결과물에 대한 질문
        //   1) 본인이 한 역할이 무언인가?
        //   2) 사용했던 기술(통신라이브러리, 프레임워크, db제어(jpa, mybaits)
        //   3) 팀워크에 대해서(갈등 상황이나 어려운 점이 있었나? 어떻게 해곃했나?)

    }
}










