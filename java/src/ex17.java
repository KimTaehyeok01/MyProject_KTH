// 클래스를 선언하려면, 클래스 밖에서 선언한다.
public class ex17 {
    // 함수를 또 선언하려면, 함수 밖에서 선언한다.
    public static void main(String[] args) {
        // 함수(Function, method(클래스안에 있는 함수, java))
        // JS와 문법이 거의 유사하다.

        // 매개변수 x, 반환타입 x
        myFunc1();

        // 매개변수 o, 반환타입 x
        myFunc2(1);

        // 매개변수 x, 반환타입 o
        // System.out.println(myFunc3());도 가능
        int result = myFunc3();
        System.out.println(result);

        // 매개변수 o, 반환타입 o
        String name = myFunc4("김태혁", "안녕");
        System.out.println(name);

        int num = myFunc5(10,20);
        System.out.println(num);
    }

    // static힘수에서 함수 호출시 static을 사용해야함.
    // 매개변수 x, 반환타입 x
    static void myFunc1() {
        System.out.println("myFunc1이 호출됨");
        return; // 반환 값이 없을 때는 리턴 생략 가능
    }

    // 매개변수 o, 반환타입 x
    static void myFunc2(int a){
        // 함수의 매개변수와 지역변수는 함수 안에서만 작동한다.
        int localvar = 20;
        System.out.println("myFunc2 함수에서 나온 숫자는 " +a +"입니다.");
    }

    // 매개변수 x, 반환타입 o
    static int myFunc3(){
        // 반환타입과 값을 일치시켜야 함.
        return 10;
    }

    // 매개변수 o, 반환타입 o
    static String myFunc4(String param1, String param2){
        return param1 + param2;
    }

    static int myFunc5(int param1, int param2){
        return param1 * param2;
    }

}










