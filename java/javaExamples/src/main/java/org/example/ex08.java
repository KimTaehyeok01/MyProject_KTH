package org.example;

public class ex08 {
    public static void main(String[] args) {
        // 비교연산자(연산 결과가 boolean임)
        // A > b : A가 B보다 큰가?
        // A < B : A가 B보다 작은가?
        // A == B : A랑 B가 같은가?
        // A != B : A랑 B가 같지 않은가?
        // A >= B : A가 B보다 크거나 같은가?  A > B || A == B
        // A <= B : A가 B보다 작거나 같은가?  A < B || A == B

        // 논리 연산자
        // AND && : ~이면서, ~이고
        // OR || : ~이거나, ~중에 하나이면, ~중에 하나라도
        // NOT ! : ~이 아닌

        // 논리표
        // T & T = T
        // T & F = F
        // F & T = F
        // F & F = F

        // T || T = T
        // T || F = T
        // F || T = T
        // F || F = F

        // !true = false
        // !false = true

        //연습문제
        //1. 10은 2의 배수인지를 true/false로 출력하시오.
        System.out.println(10 % 2 == 0);
        //2. 10은 4의 배수이면서 2의 배수인지를 true/false로 출력하시오.
        System.out.println((10 % 4 == 0) && (10 % 2 == 0));
        //3. 15는 10이상이면서 20미만인지 true/false로 출력하시오.
        System.out.println(15 >= 10 && 15 < 20);
        //이상 >=
        //이하 <=
        //초과 >
        //미만 <
        //4. 3은 9의 약수이면서 15의 약수인지를 true/false로
        System.out.println(9 % 3 == 0 & 15 % 3 == 0);

    }
}
