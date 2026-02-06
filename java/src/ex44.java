public class ex44 {
    public static void main(String[] args) {
        // throws문
        //         : 자신을 호출한 메소드에 예외처리를 떠넘기는 것.
        //         : 예외처리가 귀찮을 때 씀. 코드가 간결해진다.
        try {
            myFunc();
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    static void myFunc() throws Exception{
        System.out.println(10 / 0);
    }
}
