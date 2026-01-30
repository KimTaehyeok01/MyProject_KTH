package org.example;

public class ex13 {
    public static void main(String[] args) {
        // 이중반복문
        // 일차반복문 : 1차배열 접근
        // 이중반복문 : 2차배열 접근
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println("i: " + i + ", j: " + j);
            }
        }
        int[][] nums2D = {{1, 2}, {3, 4}};

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.println(nums2D[i][j]);
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.printf("*");
            }
            System.out.println();
        }

        //연습문제
        //1
        System.out.println("----1번 문제----");
        for (int i = 1; i <= 5; i++) {
            for (int j = 5; j > 0; j--) {
                if (j > i) {
                    System.out.print(" ");
                } else {
                    System.out.print("*");
                }
            }
            System.out.println();
        }
        //2
        System.out.println("----2번 문제----");
        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 7; j++) {
                if (i == 1 || i == 7 || j == 1 || j == 7 || j == i-4 || j == 8 - i) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}