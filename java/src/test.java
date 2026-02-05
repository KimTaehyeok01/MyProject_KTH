// 1. 부모 클래스: 스마트폰
class SmartPhone {
    int price;

    // 부모의 매개변수 생성자 (최종 목적지)
    public SmartPhone(int price) {
        // [4번] 5000원이 드디어 부모님 주머니에 도착!
        this.price = price;
    }
}

// 2. 자식 클래스: 갤럭시
class Galaxy extends SmartPhone {
    int price;

    // 자식의 기본 생성자
    public Galaxy() {
        // [1번] "나 갤럭시 기본 모델인데, 일단 5000원짜리라고 치고 옆 생성자한테 시킬게!"
        this(5000);
    }

    // 자식의 매개변수 생성자
    public Galaxy(int price) {
        // [2번] "5000원 받았네? 자, 아빠(super)부터 태어나야 하니까 아빠한테 돈 보낸다!"
        super(price);

        // [5번] "아빠 방 다 지었대? 이제 내 주머니에도 5000원 넣어야지."
        this.price = price;
    }

    void showStatus() {
        System.out.println("\n--- 최종 영수증 ---");
        System.out.println("부모 주머니(super.price): " + super.price);
        System.out.println("내 주머니(this.price): " + this.price);
    }
}


// 3. 실행 클래스
public class test {
    public static void main(String[] args) {
        System.out.println("=== 객체 생성 및 공사 시작 ===");

        // [0번] 갤럭시 기본 모델 주문!
        Galaxy myPhone = new Galaxy(10000);

        myPhone.showStatus();
    }
}















