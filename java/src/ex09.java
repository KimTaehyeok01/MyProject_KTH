import java.util.Scanner;
// import 하는 이유는 컴파일(번역, 패키징)할 때 관련 코드를 포함시킨다.
// - 실행 파일 크기를 최소화하기 위해서
// Math 패키지(클래스) import 안해도 사용 가능 - 자주 사용하기에

public class ex09 {
    public static void main(String[] args) {
        // 클래스이름 객체이름(인스턴스) = new 클래스이름(인자)
        Scanner sc = new Scanner(System.in);

        System.out.printf("이름: "); // 웰컴문구(입력문구)
        String str1 = sc.next(); // 엔터치면 한 줄 입력 끝
        System.out.println("내 이름은: " + str1);

        sc.nextLine(); // 버퍼 비우기 용도(엔터값 제거)

        System.out.printf("입력할 내용: ");
        String text = sc.nextLine();
        System.out.println("입력받은 값 = " + text);

        System.out.printf("입력할 수(double): ");
        double num1 = sc.nextDouble();
        System.out.println("num1 = " + num1);

        sc.close(); // Scanner 객체 닫기(메모리 해제)
        // 자바에서는 GC(Grbage Collector)가 자동으로 메모리 해제를 하지만
        // 외부 자원과 연결된 객체는 명시적으로 닫아주는 것이 좋다.
    }
}
