// 메서드 오버라이딩
// 의미: 상속관계에서 부모클래스의 메서드를 자식클래스가 재정의 하는 것.
//     : 부모클래스의 메서드는 무시된다는 특징이 있다.

class Cable {
    int price = 1000;

    void sale() {
        System.out.println("케이블 판매");
    }
}

class PowerCable extends Cable {
    int price = 2000;
    // 메서드 오버라이딩 - 메서드 재정의 - 생략가능
    // 컴파일러와 개발자에게 오버라이딩을 되었음을 알려주는 어노테이션임.
    @Override // 어노테이션 - 컴파일 지시어
    void sale() {
        System.out.println("파워 케이블 판매");
    }
}

public class ex30 {
    public static void main(String[] args) {

        // 자식클래스의 멤버변수가 출력.(변수 hiding)
        PowerCable cable = new PowerCable();

        // 자식클래스의 메서드가 실행. (메서드 오버라이딩)
        System.out.println("가격은 " + cable.price + "원.");

        cable.sale();
    }

}
