package Mart;

import java.util.List;
import java.util.Scanner;

public class Manager {
    List<MartInfo> list;
    Scanner sc = new Scanner(System.in);

    Manager(List<MartInfo> list) {
        this.list = list;
    }

    void productInput() {
        System.out.printf("상품명을 등록하세요: ");
        String name = sc.next();
        System.out.printf("가격을 등록하세요: ");
        int price = sc.nextInt();
        System.out.printf("재고를 등록하세요: ");
        int stock = sc.nextInt();
        list.add(new MartInfo(name, price, stock));
        System.out.println("등록이 완료되었습니다.");
    }

    void productDelete() {
        boolean found = false;
        System.out.printf("삭제할 상품명을 입력하세요: ");
        String name = sc.next();
        for (MartInfo m : list) {
            if (name.equals(m.name)) {
                list.remove(m);
                System.out.println(name + " 상품이 삭제되었습니다.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("존재하지 않는 상품입니다.");
        }
    }

    void productUpdate() {
        boolean found = false;
        System.out.printf("수정할 상품명을 입력하세요: ");
        String name = sc.next();
        for (MartInfo m : list) {
            if (name.equals(m.name)) {
                System.out.printf("가격을 등록하세요: ");
                m.price = sc.nextInt();
                System.out.printf("재고를 등록하세요: ");
                m.stock = sc.nextInt();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("존재하지 않는 상품입니다.");
        }
    }
}
