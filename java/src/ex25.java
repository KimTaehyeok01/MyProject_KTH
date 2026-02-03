//연습문제 - 싱글톤 만들기
//클래스 이름 : TossAccount
//  필드(private) : 계좌번호(1234), 고객이름(홍길동), 잔액(1000), 이자율(년3%)
//  메소드 : 입금(+100), 출금(-100), 이자계산(1년후 잔액), 잔액조회
class TossAccount {
    private String accountNumber = "1234";
    private String customerName = "홍길동";
    private int price = 1000;
    private double interestRate = 0.03;

    private static TossAccount singleton;

    public static TossAccount getInstance() {
        if (singleton == null) {
            singleton = new TossAccount();
        }
        return singleton;
    }

    public void deposit(int amount) {
        this.price += amount;
        System.out.println("입금 완료: " + amount + "원 | 현재 잔액: " + this.price + "원");
    }

    public void withdraw(int amount) {
        if (this.price >= amount) {
            this.price -= amount;
            System.out.println("출금 완료: " + amount + "원 | 현재 잔액: " + this.price + "원");
        } else {
            System.out.println( "잔액이 부족합니다.");
        }
    }

    public void calculateInterest() {
        int interest = (int)(this.price * this.interestRate);
        this.price += interest;
        System.out.println("이자 지급됨: " + interest + "원 (연 3%)");
    }

    public int getPrice() {
        return this.price;
    }
}

public class ex25 {
    public static void main(String[] args) {
        TossAccount ta = TossAccount.getInstance();
        ta.deposit(100);
        ta.withdraw(200);
        ta.calculateInterest();
        System.out.println("현재 금액은 " + ta.getPrice() + "원 입니다.");

    }
}