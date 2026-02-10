package Mart;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MartMainSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<MartInfo> list = new ArrayList<>();

        Manager manager = new Manager(list);
        MartSearch order = new MartSearch(list);

        boolean isTrue = true;

        while (isTrue) {
            System.out.println("\n----------- 마트 재고 관리 -------------");
            System.out.print("1.상품등록 2.전체조회 3.상품검색 4.수정 5.삭제 6.종료 : ");
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    manager.productInput();
                    break;

                case 2:
                    order.searchAll();
                    break;

                case 3:
                    order.searchProduct();
                    break;

                case 4:
                    manager.productUpdate();
                    break;

                case 5:
                    manager.productDelete();
                    break;

                case 6:
                    System.out.println("마트 재고 관리 시스템을 종료합니다.");
                    isTrue = false;
                    break;
            }
        }
    }
}
