public class ex43 {
    public static void main(String[] args) {
        // 예외처리(Exception Handling)
        // : 예외 - 에러(Error), 예상치 못한 오류
        // : 실행시(Runtime)에 처리 가능한 문법을 만듦

        // 1. try catch문, try catch finally문
        // 2. throws문

        // 1. 패턴
//        try{
//            예외가 발생할 만한 실행문
//        }
//        catch(예외클래스 객체){
//            예외 발생시 처리하는 실행문(에러 내용을 출력 )
//    }

        // Null Exception(널 처리 오류)
        String name = null;
        System.out.println(name);
//
        try {
            // NullPointerException: Cannot invoke "String.toLowerCase()" because "name" is null
            System.out.println(name.toLowerCase());
        } catch (Exception e) {
            System.out.println(e.getMessage()); // 예외메세지 출력
            e.printStackTrace(); // 예외 발생 경로를 출력
        }

        try {
            // 배열 인덱싱 예외
            int[] nums = {10, 20, 30};
            // ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 3
            System.out.println(nums[3]);

            // ArithmeticException: / by zero
//             System.out.println(10/0);
            // 0으로 나누기
        }
        catch (ArithmeticException e){
            System.out.println("ArithmeticException");
            System.out.println(e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException");
            System.out.println(e.getMessage());
        }
//         모든 Exception은 Exception클래스를 상속받는다.
        catch (Exception e) {
            System.out.println(e.getMessage());
//            e.printStackTrace();
        }

        // finally {
        //       예외가 발생하든지 안하든지 무조건 수행.
        //       수행하던 코드(자원-메모리)를 정리하는 코드.
        // }
        finally{
            System.out.println("정리하는 코드");
            // 예) scan.close();
        }

    }

}
