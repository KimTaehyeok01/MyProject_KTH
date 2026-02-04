// 상속관계에서 생성자 함수
class Energy {
    int price = 1000;

    // 기본 생성자, 매개변수가 없는 생성자 함수
    public Energy() {
        System.out.println("Energy()");
    }

    // 매개변수가 있는 생성자함수(오버로딩)

    public Energy(int price) {
        this.price = price;
        System.out.println("Energy(int price)");
    }
}

class WindEnergy extends Energy {
    int price = 3000;

    public WindEnergy() {
        System.out.println("WindEnergy()");
    }

    public WindEnergy(int price) {
        // super : 부모클래스 객체를 주소를 가르키는 예약어
//         super();  // 부모 기본생성자 호출
        // 부모클래스의 생성자함수를 호출함으로 값을 초기화한다.
        super(price); // 부모 매개생성자의 호출, 위치는 맨 위, 두 번 호출 불가능

        // this. : 자신의 멤버(변수,함수)에 접근
        // this() : 자신의 생성자함수 실행
        // super. : 부모의 멤버(변수,힘수)에 접근
        // super() : 부모의 생성자함수 실행
        this.price = price;
        System.out.println("WindEnergy(int price)");
    }
}

public class ex31 {
    public static void main(String[] args) {
        // 기본생성자 함수를 이용한 객체생성
//        Energy energy1 = new Energy();
//
//        // 매개변수가 있는 생성자를 이용한 객체 생성
//        Energy energy2 = new Energy(2000);

//        WindEnergy energy3 = new WindEnergy();
        WindEnergy energy4 = new WindEnergy(4000);

    }

}
