package Mart;

import java.util.List;
import java.util.Scanner;

public class MartSearch {
    List<MartInfo> list;
    Scanner sc = new Scanner(System.in);

    MartSearch(List<MartInfo> list) {
        this.list = list;
    }

    void searchProduct() {
        boolean found = false;
        System.out.printf("상품명을 입력하세요: ");
        String name = sc.next();
        for (MartInfo m : list) {
            if (name.equals(m.name)) {
                m.print();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("존재하지 않는 상품입니다.");
        }
    }

    void searchAll() {
        for (MartInfo m : list) {
            m.print();
        }
    }
}
