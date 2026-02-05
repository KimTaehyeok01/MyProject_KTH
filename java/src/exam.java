/*[고객사 요구사항 문서]
 * * 1. 우리 공장에는 '용접 로봇'이 있습니다. 로봇은 기본적으로 '이름'이 있어야 하고,
 * 공통적으로 "위이잉~" 하며 기동하는 소리를 냅니다.
 * * 2. 모든 로봇은 '전원 켜기'와 '전원 끄기' 버튼이 있어야 합니다.
 * 특히, 전원을 끌 때는 "안전하게 시스템을 종료합니다."라는 안내가 공통적으로 나와야 합니다.
 * * 3. '용접 로봇(WeldingRobot)'은 기동할 때 "불꽃을 튀기며 용접을 시작합니다!"라고 특별하게 말해야 하고,
 * 이 로봇만의 특별한 기능인 '용접하기(weld)'를 누르면 "지지직... 쇠를 붙입니다."라고 출력되어야 합니다.
 * * 4. 공장 관리자는 '리모컨' 타입으로 로봇의 전원을 끄고 켤 수 있어야 하지만,
 * 로봇이 가진 특별한 기능(용접하기 등)을 쓰려면 로봇을 직접 점검(형변환)해야 합니다.
 * * 5. 가끔 급하게 '청소 로봇'이 필요할 때가 있는데, 이건 정식 클래스로 만들기 귀찮으니
 * 메인 함수에서 바로 '익명'으로 하나 만들어서 써주세요.
 * (전원을 켜면 "먼지를 흡입합니다."라고 나오게 해주세요.)
 * * [주의사항]
 * - 로봇의 공통 속성과 버튼 규격을 어떻게 나눌지(인터페이스 vs 추상 클래스) 직접 판단하세요.
 * - '전원 끄기'의 안내 문구는 모든 로봇이 똑같으므로 한 곳에서 관리하고 싶습니다.*/
/*
--- 1. 용접 로봇 기동 (업캐스팅 상태) ---
내 이름은 용접이이야.
위이잉~ 불꽃을 튀기며 용접을 시작합니다!  <-- (부모의 소리 + 자식의 특별한 소리)
용접 로봇의 전원을 켭니다.

--- 2. 용접 로봇 특수 기능 (다운캐스팅 상태) ---
지지직... 쇠를 붙입니다.

--- 3. 청소 로봇 기동 (익명 객체) ---
먼지를 흡입합니다.

--- 4. 모든 로봇 공통 종료 (디폴트 메서드 활용) ---
안전하게 시스템을 종료합니다.
안전하게 시스템을 종료합니다.
 */

interface RobotRemoteControl{
    void turnOn();
    default void turnOff() {
        System.out.println("안전하게 시스템을 종료합니다.");
    }
}
abstract class Robot{
    public String name;

    abstract void weld();
    public Robot(String name){
        this.name= name;
    }
    void startSound() {
        System.out.print("위이잉~ ");
    }
}

class WeldingRobot extends Robot implements RobotRemoteControl{
    public WeldingRobot(String name){
        super(name);
        this.name= name;
        System.out.println("내 이름은 " +this.name+"이야.");
        super.startSound();
        System.out.println("불꽃을 튀기며 용접을 시작합니다! ");
    }
    public void turnOn(){
        System.out.println("용접 로봇의 전원을 켭니다.");
    }
    public void weld(){
        System.out.println("지지직... 쇠를 붙입니다.");
    }
}


public class exam {
    public static void main(String[] args) {
        RobotRemoteControl rc = new WeldingRobot("용접");
        rc.turnOn();

        Robot rb = (Robot)rc;
        rb.weld();

        RobotRemoteControl rc1 = new RobotRemoteControl(){
            public void turnOn(){
                System.out.println( "먼지를 흡입합니다.");
            }
            public void turnOff(){
                System.out.println("안전하게 시스템을 종료합니다.");
            }
        };

        rc1.turnOn();
        rc1.turnOff();

    }
}
