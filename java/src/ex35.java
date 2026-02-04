class People {
    void think() {
        System.out.println("사람이 생각한다.");
    }
}

class Man extends People {
    @Override
    void think() {
        System.out.println("남자가 생각한다.");
    }

    void shave() {
        System.out.println("면도한다.");
    }
}

class Woman extends People {
    @Override
    void think() {
        System.out.println("여자가 생각한다.");
    }

    void makeup() {
        System.out.println("화장한다.");
    }
}

public class ex35 {
    public static void main(String[] args) {

        // 업캐스팅
        People people = new Man();
        people.think(); // 오버라이딩된 think를 호출한다.
        // people.shave(); // 호출불가

        //다운캐스팅
        Man man = (Man)people;
        man.think();
        man.shave(); // 호출 가능

        // 다형성을 이용하여, Man객체, Woman객체를 모두 전달할 수 있다.
        //   왜? Man과 Woman은 모두 People를 상속받았기 때문이다.
        // 업캐스팅을 하면, 모두 People타입이고 동일타입이다.
//        myFunc(new People());
        myFunc(new Man());
        myFunc(new Woman());
    }



    static void myFunc(People p){
        // instanceof : 객체 타입을 확인하는 연산자(true/false)
        //  : 특정클래스의 인스턴즈인지 or 그 클래스를 상속받은 자식클래스인지 확인하여
        //     true/false로 반환하는 연산.

        if(p instanceof Man){
            Man m = (Man)p; // 다운캐스팅
            m.shave();
        }
        else if(p instanceof Woman){
            Woman w = (Woman)p; // 다운캐스팅
            w.makeup();
        }
    }

}
