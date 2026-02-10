package Mart;

public class MartInfo {
    String name;
    int price;
    int stock;

    public MartInfo(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    void print(){
        System.out.printf("%s 상품의 가격은 %d원이고 재고는 %d개 남았습니다.\n", name, price, stock);
    }
}
