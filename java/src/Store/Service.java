package Store;

import java.util.List;
import java.util.Scanner;

class Service {
    List<StoreProduct> list;
    static Scanner sc = new Scanner(System.in);

    Service(List<StoreProduct> list){
        this.list = list;
    }

    // 상품 등록
    void inputProduct() {
        System.out.printf("상품명 입력 : ");
        String name = sc.next();
        System.out.printf("가격 입력 : ");
        int price = sc.nextInt();
        System.out.printf("재고 입력 : ");
        int stock = sc.nextInt();
        list.add(new StoreProduct(name, price, stock));
    }

    // 상품 삭제
    void productDelete() {
        boolean found = false;
        System.out.printf("상품명 입력 : ");
        String name = sc.next();
        for(StoreProduct s : list){
            if(name.equals(s.name)){
                list.remove(s);
                found = true;
                break;
            }
        }
        if(!found){
            System.out.println("검색하신 상품이 존재하지 않습니다.");
        }
    }

    // 상품 수정
    void productUpdate(){
        boolean found = false;
        System.out.printf("상품명 입력 : ");
        String name = sc.next();
        for(StoreProduct s : list){
            System.out.printf("가격 입력 : ");
            s.price = sc.nextInt();
            System.out.printf("재고 입력 : ");
            s.stock = sc.nextInt();
            found = true;
        }
        if(!found){
            System.out.println("검색하신 상품이 존재하지 않습니다.");
        }
    }
}