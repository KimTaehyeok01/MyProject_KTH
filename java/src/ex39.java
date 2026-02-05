// 익명 객체(이름 없는 객체 - Anonymous Object)
//         : 1회성 메소드(코드뭉치) 재정의할 때 사용한다.
//         : 한 번 쓰고 버릴 코드느 굳이 이름을 붙일 이유가 없다.

interface NomalCar{
    void run();
}
//
//class SuperCar implements NomalCar{
//    public void run(){
//        System.out.println("슈퍼카가 달린다.");
//    }
//}

public class ex39 {
    public static void main(String[] args) {
//        NomalCar sc = new SuperCar();
//        sc.run();

        // 일회성 익명 인터페이스 구현 객체를 생성한다.
        // 이걸 익명객체라고 부른다.
        NomalCar nomalCar = new NomalCar(){
            public void run(){
                System.out.println("슈퍼카가 빨리 달린다.");
            }
        };
        nomalCar.run();
    }

}
