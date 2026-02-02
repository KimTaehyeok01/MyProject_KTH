package org.example;

public class ex16 {
    public static void main(String[] args) {

        // 2차 배열의 선언 방법
        // 1.
        int [][] arrNum1 = { {10,20,30},
                {30,40,50} };

        // 2.
        int [][] arrNum2 = new int[2][3]; // 2행 3열

        // 3.
        int [][] arrNum3 = new int[][]{ {10,20,30},
                {30,40,50} };

        // 4.
        int [][] arrNum4 = new int[2][]; // 2행인데, 열의 개수가 없음
        // 열은 나중에 초기화함
        arrNum4[0] = new int[3];
        arrNum4[1] = new int[3];

        //  int [][] arrNum5 = new int[][]; 이 표현은 안됨

        // 행의 길이
        System.out.println(arrNum4.length);

        //열의 길이
        System.out.println(arrNum4[0].length);
        System.out.println(arrNum4[1].length);

        // 2차 배열의 순환
        for(int i = 0; i < arrNum1.length; i++){
            for(int j = 0; j < arrNum1[i].length; j++){
                System.out.println(arrNum1[i][j]);
            }
        }
        System.out.println("----------------------------");
        // 향상된 for문
        for(int [] nums : arrNum1){
            for(int num : nums){
                System.out.println(num);
            }
        }

        System.out.println("----------------------------");
        // 이건 틀림
        for(int nums : arrNum1[0]){
            for(int num : arrNum1[1]){
                System.out.println(num);
            }
        }


    }

}

