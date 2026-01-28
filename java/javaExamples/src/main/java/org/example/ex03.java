package org.example;

import java.util.Scanner;

public class ex03 {
    public static void main(String[] args) {
        // 데이터 타입(Data Type)
        // 숫자 타입(JS : number) : byte(1), int(4), short(2), float(4), long(8), double(8)
        // 문자타입(JS : String) : char
        // 문자열 타입(JS : String) : String
        // 논리형(boolean) 타입  : boolean(1)

        // JS 동적 타입 : 변수(리터럴)에 대입시 결정
        // let a = 10 -> number타입
        // JAVA 정적 타입 : CODE에서 타입을 결정
        int a = 10;
        System.out.println("a: " + a);

        long my_long = 20L; // L은 long을 의미. 8바이트 long으로 초기화한다.
        System.out.println("my_long: " + my_long);

        short my_short = 32767;
        System.out.println("my_short : " + my_short);

        byte my_byte = 40;
        System.out.println("my_byte : " + my_byte);
        System.out.println(my_byte);

        // 실수형
        float my_float = 3.14f;
        System.out.println("my_float: " + my_float);
        System.out.printf("my_float: %.2f", my_float);

        // 더블형
        double my_double = 6.14;
        System.out.println("my_double: " + my_double);

        boolean mybool = true;
        System.out.println("mybool" + mybool);

        // 문자형
        char ch = '가';
        System.out.println("ch: " + ch);
        System.out.println("ch: " + (int) ch); // 형변환-> 유니코드에서 '가'는 44032
        System.out.println("ch: " + (char) 0xAC00);
        // 유니코드표의 첫 장은 아스키코드표이다.
        System.out.println((char) 65); //A
        System.out.println((int) 'A');

        // 문자열
        String st = "안녕하세요.";
        System.out.println("st: " + st);

        // 코드 재정렬(prettier formatter)
        //CTRL + ALT + L

        Scanner sc = new Scanner(System.in);
        System.out.printf("점수 입력: ");
        int score = sc.nextInt();

        if(score > 90){
            System.out.println("A");
        }else if(score > 90){
            System.out.println("B");
        }
        else if(score > 80){
            System.out.println("C");
        }
        else if(score > 70){
            System.out.println("D");
        } else{
            System.out.println("F");
        }
    }
}
