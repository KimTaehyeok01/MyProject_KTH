public class ex42 {
    public static void main(String[] args) {
        // 래퍼클래스(wrapper class)
        // 1. 기본 자료형 8개를 감싸서 클래스로 만든 것
        // 2. 클래스로 만들어서 기능 확장, 다형성 확보

        // 기본자료형 8개 : 정수형 4개(int,long,short,byte), 실수형 2개(float,double), 문자형(char), 논리형(boolean)
        // 래퍼클래스 8개: Integer Long Short Byte, Float Double, Character Boolean
        // 문자열 : String
        // 숫자 클래스 : Number <= int, long, short, byte, float, double

        // 래퍼클래스의 선언
        Integer intValue1 = new Integer(10); // JDK 9버전부턴 비추천(지원중단예정)
        Integer intValue2 = 10;
        System.out.println(intValue2);
        System.out.println(intValue2.shortValue());
        System.out.println(intValue2.doubleValue());

        Number number1 = 30;
        Number number2 = 3.14f;
        Number number3 = 3.14d;

        System.out.println(number1);
        System.out.println(number2.intValue());
        System.out.println(number3.floatValue());



    }

}
