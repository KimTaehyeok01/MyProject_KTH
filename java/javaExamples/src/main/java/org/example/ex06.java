package org.example;

public class ex06 {
    public static void main(String[] args) {
        // 연산자의 종류
        // 단항 : 증감연산자(++ --) !(논리반전) (타입)
        // 산술 : + - * / %
        // 비교 : < > >= <= == != instanceOf(객체비교연산자)
        // 논리 : && ||
        // 삼항 ? :
        // 대입 : = 복합대입연산자(+= -= *= /= %=)
        // 우선순위 : 괄호 - 단항 - 산술 - 비트시프트 - 비교(관계) - 논리 - 대입

        // 단항연산자
        int i = 10;
        i++;
        System.out.println("i: " +i);
        i--;
        System.out.println("i: " +i);

        // 논리반전연산자
        System.out.println(!true);
        System.out.println(!false);

        // 형변환연산자
        int j = 20;
        short s = (short)j ;
        System.out.println(j);
    }
}
