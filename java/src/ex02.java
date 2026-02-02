public class ex02 {
    public static void main(String[] args) {
        System.out.println("화면출력");
        System.out.println("화면" + "출력");
        System.out.println("10" + 10);
        System.out.println(10 + 10);

        System.out.println("안녕하세요");

        // 형식화된 출력문(formatted print)
        System.out.printf("나이 : %d \n", 20);
        System.out.printf("인사 : %s \n", "안녕");
        System.out.printf("대답 : %c \n", '응');
        System.out.printf("몸무게 : %.1f \n", 71.2);
        System.out.printf("지수형 표현 e : %e \n", 200.0);
        //자릿수 맞추기
        System.out.printf("%5d\n", 123); // 5자릿수(공백으로 맞춤)
        System.out.printf("%05d\n", 123); // 5자릿수(0으로 맞춤)
        System.out.printf("%.2f\n", 123.45); // 5자릿수(0으로 맞춤)
    }
}
