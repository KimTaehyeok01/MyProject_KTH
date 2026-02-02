import java.util.Random;

public class ex10 {
    public static void main(String[] args) {
        // 조건문 = JS와 문법이 거의 유사
        // 1. 단순 if문
        if (10 < 20) {
            System.out.println("10은 20보다 작다.");
        }
        // 실행문이 한 줄일 때는 중괄호 생략 가능
        if (20 > 10)
            System.out.println("20은 10보다 크다.");

        // 2. if else문
        if (10 > 20) {
            System.out.println("10 > 20");
        } else System.out.println("10 <= 20");

        // 3. if else if문
        int score = 90;
        if (score >= 90) {
            System.out.println("90점 이상");
        } else if (score >= 80) {
            System.out.println("80점 이상");
        } else {
            System.out.println("그외 나머지");
        }

        // 4. 중첩 if문(if문 안에 if문(1,2,3))
        if (true) { // 조건 1
            if (false) { // 조건 2
                // 조건 1이 참이고 조건 2가 참일 때
            } else if (true) { // 조건 3
                // 조건 1이 참이고 조건 2가 거짓이고 조건 3이 참일 때
            }
        }

        // 자바에서 랜덤 수 발생
        // 1. Math.random() 함수 이용 : 0.0 이상 1.0 미만의 실수 발생
        System.out.println(Math.random() * 6); // 0.0 ~ 5.9999999

        // 2. Random 클래스 이용 : import java.util.Random; 필요
        //     rand.nextInt(6) : 0 이상 6 미만의 정수 발생
        Random rand = new Random();
        int num = rand.nextInt(6); // 0~5의 랜덤 정수 발생
        System.out.println("랜덤 정수: " + num);

        //연습문제
        //1. 철수와 영희가 주사위 놀이를 하고 있다.
        // 주사위 2개를 던져서,
        // 두개 다 짝수가 나오면 철수 승!
        // 두개 다 홀수가 나오면, 영희 승!
        // 그외의 경우는 무승부! 이다.
        // 게임의 결과를 출력하시오.
        System.out.println("---1번 문제---");
        int chulsooDie1 = rand.nextInt(6) + 1;
        int chulsooDie2 = rand.nextInt(6) + 1;
        int yeongheeDie1 = rand.nextInt(6) + 1;
        int yeongheeDie2 = rand.nextInt(6) + 1;

        System.out.printf("철수 주사위1 수 : %d\n", chulsooDie1);
        System.out.printf("철수 주사위2 수 : %d\n", chulsooDie2);
        System.out.printf("영희 주사위1 수 : %d\n", yeongheeDie1);
        System.out.printf("영희 주사위1 수 : %d\n", yeongheeDie2);
        boolean chulsooEven = (chulsooDie1 % 2 == 0) && (chulsooDie2 % 2 == 0);
        boolean yeongheeOdd = (yeongheeDie1 % 2 != 0) && (yeongheeDie2 % 2 !=0);
        if (chulsooEven) {
            System.out.println("철수 승!");
        } else if (yeongheeOdd) {
            System.out.println("영희 승!");
        } else {
            System.out.println("무승부!");
        }
        //2.
        //철수와 영희을 주사위게임을 하고 있다.
        //주사위 2개를 철수가 던지고,
        //주사위 2개를 영희도 던진다.
        //게임룰 : 첫번째 주사위는 십의 자릿수로하고,
        //        두번째 주사위는 일의 자릿수로 해서,
        // 더 높은 점수를 가진 사람이 승리한다.
        //출력값 예시 :
        //        철수 주사위1 수 : 1
        //        철수 주사위2 수 : 3
        //        철수의 점수는 13
        //        영희 주사위1 수 : 3
        //        영희 주사위2 수 : 4
        //        영희의 점수는 34
        //        영희 승!
        System.out.println("---2번 문제---");
        System.out.println("철수 주사위 1 수: " +chulsooDie1);
        System.out.println("철수 주사위 2 수: " +chulsooDie2);
        System.out.println("영희 주사위 1 수: " +yeongheeDie1);
        System.out.println("영희 주사위 2 수: " +yeongheeDie2);
        int chulsooScore = chulsooDie1 *10 + chulsooDie2;
        int yeongheeScore = yeongheeDie1 *10 + yeongheeDie2;
        System.out.println("철수의 점수는 " + chulsooScore);
        System.out.println("영희의 점수는 " + yeongheeScore);
        if(chulsooScore > yeongheeScore){
            System.out.println("철수 승!");
        }else if(chulsooScore < yeongheeScore){
            System.out.println("영희 승!");
        }
        // 자바 계열에선 카멜케이스를 많이 쓴다.
        // ex) myBirthDay, userName, totalPrice
    }
}
