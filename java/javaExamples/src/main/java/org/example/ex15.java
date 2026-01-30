package org.example;

import java.util.Arrays;
import java.util.Collections;

public class ex15 {
    public static void main(String[] args) {
        // ES6
        // 배열 + 리스트 하나로 통합되어 있다.
        // 배열 : 데이터가 순차적으로 모여있는 자료구조
        // 리스트 : 배열 + 중간에 삽입, 삭제가 가능
        // 자바
        // 배열과 리스트가 분리되어 있다.

        // 정수형 1차 배열
        // 선언방법
        int[] num = new int[5];
        int num2[] = new int[3];
        int[] num3 = {10, 20, 30};
        int[] arrNum4 = new int[]{10, 20, 30};

        num[0] = 20;
        System.out.println(num[0]);
        System.out.println(num3[2]);
        System.out.println(arrNum4[0]);

        // 안되는 경우
        // int [] a;
        // a = {1,2,3};

        for (int i = 0; i < arrNum4.length; i++) {
            System.out.println("arrNum4: " + arrNum4[i]);
        }

        // 향상된 for문, for-each문
        for (int a : num3) {
            System.out.println(a);
        }

        // 배열의 정렬(sort)
        int[] nums = {10, 30, 20, 50, 40};
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        System.out.println(nums[4]);

        // 내림차순 정렬(reverse)
        Integer [] nums2 = {10, 30, 20, 50, 40};
        Arrays.sort(nums2, Collections.reverseOrder());
        System.out.println(Arrays.toString(nums2));

        // Integer는 정수형 래퍼(wrapper)클래스이다.
        // 클래스로서 int(기본형)에 없는 기능을 확장한 클래스(객체)이다.
        // int를 클래스로 만든 것이라고 생각하면 편함.

        // 자바 자체를 클래스 지원 언어(OOP)이다.
        // int(원시형)이 클래스로 만들면, 다형성을 이용한 데이터 이동이 편리하다.
        Integer num4 = 10;
    }
}
