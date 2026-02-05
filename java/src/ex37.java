// 인터페이스(interface)
//                    : 가상함수만 존재한다(일반함수 x)
// 추상화 클래스와 인터페이스는 둘다 추상메소드(가상함수)
// 설계와 구현의 관점을 둘다 가진다.
//              추상화메소드        인터페이스
// 1. 가상함수       o                 o
// 2. 일반함수       o                 o
// 3. 예약어    abstract class      interface
//             abstract 메소드명    abstract 생략가능
// 4. 상속        extends(상속)     implements(구현)
// 5. 다중상속       x                 o
// 6. 접근제한자    전부 가능         public만 가능
// 7. 필드선언      다 가능          public static만 가능

interface Drawing{
    abstract void draw();
    public abstract void stketch(); // public abstract 생략 가능

    // void paint(){} 일반함수는 안됨
}

class Painter implements Drawing{
    @Override
    public void draw() {
        System.out.println("드로윙한다.");
    }

    @Override
    public void stketch() {
        System.out.println("스캐치한다.");
    }
}
public class ex37 {
    public static void main(String[] args) {
        Drawing drawing = new Painter();

        drawing.draw();
        drawing.stketch();
    }

}
