import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Store {
    String name;
    int price;
    int stock;

    public Store(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;

    }

    void printSearch() {
        System.out.printf("상품명: %s | 가격: %d원 | 수량: %d개\n", name, price, stock);
    }

    void printSelect() {
        System.out.printf("상품명: %s | 가격: %d원 | 수량: %d개 | 총가치: %d원\n", name, price, stock, (price * stock));
    }
}

public class listStoretestexam {
    // 연습문제 - 편의점 재고 관리 프로그램
// Product 클래스(상품명, 가격, 수량)를 만들고 ArrayList를 사용하여 재고를 관리해보자.
// 힌트: 전체 출력 시 '총 가치(가격 x 수량)'도 같이 계산해서 보여주면 됨.

// 입출력 예시
// ----------- 편의점 재고 관리 -------------
// 1.상품등록 2.전체조회 3.상품검색 4.수정 5.삭제 6.종료 : 1
// 상품명 : 새우깡
// 가격 : 1500
// 수량 : 10
// [새우깡]이 등록되었습니다.

// ----------- 편의점 재고 관리 -------------
// 1.상품등록 2.전체조회 3.상품검색 4.수정 5.삭제 6.종료 : 2
// 상품명: 새우깡 | 가격: 1500원 | 수량: 10개 | 총가치: 15000원
// 상품명: 콜라   | 가격: 2000원 | 수량: 5개  | 총가치: 10000원

// ----------- 편의점 재고 관리 -------------
// 1.상품등록 2.전체조회 3.상품검색 4.수정 5.삭제 6.종료 : 3
// 검색할 상품명 : 감자깡
// [감자깡]을 찾을 수 없습니다.

// ----------- 편의점 재고 관리 -------------
// 1.상품등록 2.전체조회 3.상품검색 4.수정 5.삭제 6.종료 : 3
// 검색할 상품명 : 새우깡
// [검색 결과] 상품명: 새우깡 | 가격: 1500원 | 수량: 10개

// ----------- 편의점 재고 관리 -------------
// 1.상품등록 2.전체조회 3.상품검색 4.수정 5.삭제 6.종료 : 4
// 수정할 상품명 : 새우깡
// 변경할 가격 : 2000
// 변경할 수량 : 50
// [새우깡] 정보가 수정되었습니다.

// ----------- 편의점 재고 관리 -------------
// 1.상품등록 2.전체조회 3.상품검색 4.수정 5.삭제 6.종료 : 5
// 삭제할 상품명 : 콜라
// [콜라] 데이터가 삭제되었습니다.

    // ----------- 편의점 재고 관리 -------------
// 1.상품등록 2.전체조회 3.상품검색 4.수정 5.삭제 6.종료 : 6
// 프로그램이 종료되었습니다.
    public static List<Store> list = new ArrayList<>();
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        boolean isTrue = true;

        while (isTrue) {
            System.out.println("----------- 편의점 재고 관리 -------------");
            System.out.printf("1.상품등록 2.전체조회 3.상품검색 4.수정 5.삭제 6.종료 : ");

            try {
                int num = sc.nextInt();
                switch (num) {
                    case 1:
                        inputInfo();
                        break;
                    case 2:
                        selectAll();
                        break;
                    case 3:
                        searchPrint();
                        break;
                    case 4:
                        updateProduct();
                        break;
                    case 5:
                        removeProduct();
                        break;
                    case 6:
                        System.out.println("편의점 재고 관리 프로그램을 종료합니다.");
                        isTrue = false;
                        break;
                    default:
                        System.out.println("메뉴 번호를 잘못 입력하셨습니다. 다시 입력하세요.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("숫자만 입력해야 합니다! 다시 시도하세요.");
                sc.nextLine();
            }

        }
    }

    static void inputInfo() {
        System.out.print("삼품명 입력: ");
        String snackName = sc.next();
        System.out.print("가격 입력: ");
        int snackprice = sc.nextInt();
        System.out.print("재고 입력: ");
        int snackstock = sc.nextInt();
        list.add(new Store(snackName, snackprice, snackstock));
    }

    static void selectAll() {
        for (Store s : list) {
            s.printSelect();
        }
    }

    static void searchPrint() {
        boolean found = false;
        System.out.print("삼품명 입력: ");
        String snackName = sc.next();
        for (Store s : list) {
            if (s.name.equals(snackName)) {
                s.printSearch();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("검색하신 상품명이 없습니다.");
        }
    }

    static void updateProduct() {
        boolean found = false;
        System.out.print("삼품명 입력: ");
       try{
           String snackName = sc.next();
           for (Store s : list) {
               if (snackName.equals(s.name)) {
                   System.out.print("가격 입력: ");
                   int snackprice = sc.nextInt();
                   System.out.print("재고 입력: ");
                   int snackstock = sc.nextInt();
                   s.price = snackprice;
                   s.stock = snackstock;
                   System.out.println("상품 수정 완료!");
                   found = true;
                   break;
               }
           }
           if (!found) {
               System.out.println("검색하신 상품명이 없습니다.");
           }
       }
       catch (InputMismatchException e){
           System.out.println("숫자만 입력해야 합니다! 다시 시도하세요.");
           sc.nextLine();
       }
    }

    static void removeProduct() {
        boolean found = false;
        System.out.print("삼품명 입력: ");
        String snackName = sc.next();
        for (Store s : list) {
            if (snackName.equals(s.name)) {
                list.remove(s);
                System.out.println("상품 삭제 완료!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("검색하신 상품명이 없습니다.");
        }
    }
}
