public class ex45 {
    public static void main(String[] args) {
        // 사용자 정의 예외

        try {
            Myfunc();
        } catch (Exception e) {
            // 다운캐스팅을 통해서  MyException 객체어 접근.
            MyException me = ( MyException )e;
            me.printMessage();
            e.printStackTrace();
        }

    }
    static void Myfunc() throws Exception{
        MyException e = new MyException();
        e.message = "사용자 정의 예외입니다.";
        throw e; // 예외 강제 발생
    }
}

// 일반적인 Exception외에 기능 추가 가능 - 사용자 정의 예외
class MyException extends Exception{
    String message = "";

    public void printMessage(){
        System.out.println(this.message);
    }
}
