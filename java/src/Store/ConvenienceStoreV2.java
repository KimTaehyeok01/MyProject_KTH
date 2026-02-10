package Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConvenienceStoreV2 {
    public static void main(String[] args) {
        List<StoreProduct> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        boolean isTrue = true;

        SelectProduct selectproduct = new SelectProduct(list);
        Service service = new Service(list);

        while (isTrue) {
            System.out.println("\n----------- 편의점 재고 관리 -------------");
            System.out.print("1.상품등록 2.전체조회 3.상품검색 4.수정 5.삭제 6.종료 : ");
            int num = sc.nextInt();

            switch (num) {
                case 1:
                    service.inputProduct();
                    break;
                case 2:
                    selectproduct.selectAll();
                    break;
                case 3:
                    selectproduct.selectProduct();
                    break;
                case 4:
                    service.productUpdate();
                    break;
                case 5:
                    service.productDelete();
                    break;
                case 6:
                    System.out.println("프로그램 종료!");
                    isTrue = false;
                    break;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
}