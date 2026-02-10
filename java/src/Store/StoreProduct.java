package Store;

class StoreProduct {
    String name;
    int price;
    int stock;

    public StoreProduct(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    void print() {
        System.out.printf("상품명: %s | 가격: %d원 | 수량: %d개\n", name, price, stock);
    }
}