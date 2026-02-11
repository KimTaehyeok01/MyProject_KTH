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