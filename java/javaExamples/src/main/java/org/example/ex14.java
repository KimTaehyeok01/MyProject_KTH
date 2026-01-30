package org.example;

public class ex14 {
    public static void main(String[] args) {
        // while문 do-while문

        // while문 패턴
        // 초기화문;
        // while(조건식){
        // 실행문;
        // 증감문;
        // }
        int i = 0;
        while (i < 5) {
            System.out.println("i: " + i);
            i++;
        }

        // 적어도 한 번은 수행후에 조건 비교한다.
        int j = 0;
        do {
            System.out.println("j: " +j);
            j++;
        }
        while(j < 5);
    }
}
