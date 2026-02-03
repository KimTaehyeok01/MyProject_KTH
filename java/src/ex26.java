// 생성자 함수(Constructor)
//           : 클래스가 생성될 때(new) 자동으로 호출디는 메소드
//           : 메소드 이름은 클래스와 동일하고
//           : 용도는 필드(멤버변수)를 초기화 할 때

class Book{
    int price = 1000;  // 속성 : 필드

    // 생성자함수 패턴
    // public 반환타입(x) 클래이름과 동일 : public Book{}
     Book(int price){
         System.out.println(this.price = price);
         System.out.println("생성자함수 자동 호출");
    }
    void read(){ // 행동 : 메소드
        System.out.println("책을 읽는다.");
    }

}
public class ex26 {
    public static void main(String[] args) {
        Book book = new Book(2000);
        book.read();
    }

}
