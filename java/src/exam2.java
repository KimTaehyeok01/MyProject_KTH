/*
 * [고객사 요구사항 문서]
 * 1. '스마트 TV'와 '스마트 에어컨'이 있습니다. 모든 가전은 기본적으로 '제조사' 이름이 붙어 있습니다.
 * 2. 모든 가전은 기동할 때 공통적으로 "시스템 부팅 중..."이라는 메시지를 띄웁니다.
 * 3. 가전 제어 규격(인터페이스)에는 '전원 켜기'와 '작동하기' 버튼이 있습니다.
 * 4. 특히 '작동하기' 버튼은 가전마다 하는 일이 다릅니다.
 * - TV: "채널을 변경합니다."
 * - 에어컨: "냉방을 시작합니다."
 * 5. '스마트 TV'만의 특별한 기능: '유튜브 실행(youtube)' 메서드를 누르면 "유튜브 앱을 켭니다."라고 출력됩니다.
 * 6. 메인 시스템(Main)에서는 '리모컨' 타입으로 TV를 켜고 작동시키세요.
 * 그 후, TV를 직접 점검(형변환)해서 유튜브 기능을 실행해보세요.
 * 7. 가끔 '스마트 조명'이 필요한데, 클래스 만들기 귀찮으니 '익명 객체'로 처리해주세요.
 * (작동하기를 누르면 "조명을 노란색으로 바꿉니다."라고 나오게 하세요.)
 */
/*
--- 1. 스마트 TV 가동 (리모컨 타입으로 제어) ---
[삼성] 시스템 부팅 중...  <-- (부모의 공통 메서드 실행)
TV 전원을 켭니다.        <-- (자식의 turnOn 실행)
채널을 변경합니다.       <-- (자식의 operate 실행)

--- 2. TV 특수 기능 점검 (형변환) ---
유튜브 앱을 켭니다.      <-- (TV 전용 기능)

--- 3. 스마트 에어컨 가동 ---
[LG] 시스템 부팅 중...   <-- (부모의 공통 메서드 실행)
에어컨 전원을 켭니다.
냉방을 시작합니다.

--- 4. 스마트 조명 가동 (익명 객체 활용) ---
조명을 노란색으로 바꿉니다.  <-- (익명 객체의 operate 실행)
 */

interface Products{
    void turnOn();
    void move();
}
abstract class BrandProducts{
    public String name;
    public BrandProducts(String name){
        this.name = name;
    }
   abstract void action();
     void booting(){
        System.out.println( "시스템 부팅 중...");
    }
}

class SmartTV extends BrandProducts implements Products{
    public void turnOn(){
        System.out.println("TV 전원 켜기");
        super.booting();
    }
    public SmartTV(String name){
        super(name);
        this.name = name;
    }
    public void move(){
        System.out.println("작동하기");
    }
    void action(){
        System.out.println("채널을 변경합니다.");
    }
    void youtube(){
        System.out.println("유튜브 앱을 켭니다.");
    }

}
class Aircon extends BrandProducts implements Products{
    public void turnOn(){
        System.out.println("에어컨 전원 켜기");
        super.booting();
    }

    public Aircon(String name){
        super(name);
        this.name = name;
    }
    public void move(){
        System.out.println("작동하기");
    }
    void action(){
        System.out.println("냉방을 시작합니다.");
    }
}

public class exam2 {
    public static void main(String[] args) {
        System.out.println("--- 1. 스마트 TV 가동 ---");
        Products pd = new SmartTV("삼성TV");
        pd.turnOn();
        SmartTV sm = (SmartTV)pd;
        sm.action();

        System.out.println("--- 2. TV 특수 기능 점검 ---");
        SmartTV tv = (SmartTV)pd;
        tv.youtube();

        System.out.println("--- 3. 스마트 에어컨 가동 ---");
        Products pd1 = new Aircon("LG에어컨");
        pd1.turnOn();
        Aircon ac = (Aircon)pd1;
        ac.action();

        System.out.println("--- 4. 스마트 조명 가동 ---");
        Products pd10 = new Products(){
            public void turnOn(){
                System.out.println("스마트 조명을 켭니다.");
            }
          public void move(){
              System.out.println("조명을 노란색으로 바꿉니다.");
          }
        };

        pd10.turnOn();
        pd10.move();
    }

}
