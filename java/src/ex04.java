public class ex04 {
    public static void main(String[] args) {
        // 형 변환 Type Casting
        // 형 변환 공식
        // 1. 작은 정수형 -> 큰 정수형
        // 2. 큰 정수형 -> 작은 정수형 (표현 범위 벗어나면 값 잘림)
        // 3. 실수형 -> 정수형 (값 잘림, 소숫점 날아감)
        // 4. 정수형 -> 실수형(문제 없음)

        // 자동(암묵적) 형 변환 : 대입(산술)연산자를 통해 자동으로 형 변환됨.
        // 수동(명시적) 형 변환 : 형 변환 연산자를 통해 형 변환

        // 자동 형 변환
        // 1. 같은 타입끼리
        // ex) int * int = int
        int a = 10 * 20;
        System.out.println("a: " + a);
        // ex) long * long = long
        long a2 = 10l * 10l;
        System.out.println("a2: " + a2);

        // 2. 다른 타입끼리
        // ex) int * long = long
        float b = 10 * 20.4f;
        System.out.println("b: " + b);
        // ex) long * float = float
        float b2 = 10l * 3.14f;
        System.out.printf("b2: %.2f\n", b2);

        // 자바스크립트처럼 typeof로 데이터 타입 알아보기
        System.out.println(((Object)a).getClass().getSimpleName());
        System.out.println(((Object)b).getClass().getSimpleName());
    }
}
