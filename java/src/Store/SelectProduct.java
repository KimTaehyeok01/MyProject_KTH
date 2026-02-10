package Store;

import java.util.List;
import java.util.Scanner;

class SelectProduct{
    List<StoreProduct> list;
    static Scanner sc = new Scanner(System.in);

    SelectProduct(List<StoreProduct> list){
        this.list = list;
    }

    // 상품 전체 조회
    void selectAll(){
        for(StoreProduct s : list){
            s.print();
        }
    }

    // 상품 검색
    void selectProduct(){
        boolean found = false;
        System.out.printf("상품명 입력 : ");
        String name = sc.next();
        for(StoreProduct s : list){
            if(name.equals(s.name)){
                s.print();
                found = true;
                break;
            }
        }
        if(!found){
            System.out.println("검색하신 상품이 존재하지 않습니다.");
        }
    }
}