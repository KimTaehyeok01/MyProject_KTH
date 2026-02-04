// final 키워드
// 1. final 변수 : 상수형 변수(불변)
// 2. final 클래스 : 상속 불가
// 3. final 메서드 : 오버라이딩 불가

class FinalClass {
    String name = "파이널 클래스";
    int age; // 일반변수, 초기값을 안써도 무방함.
    final int price = 1000; // 상수형 변수, 초기값 필수!(선언과 동시에)

    final void disp(){

    }
}

class LastClass extends FinalClass {
// final 클래스 'FinalClass'(으)로부터 상속할 수 없습니다

    // @Override
    // 'disp()'은(는) 'FinalClass'에서 'disp()'을(를) 재정의할 수 없습니다. 재정의된 메서드는 final입니다
    // void disp(){}
}

public class ex32 {
    public static void main(String[] args) {
        System.out.println(new FinalClass().name);
    }

}
