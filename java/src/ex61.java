import java.util.function.BiFunction;

public class ex61 {
    public static void main(String[] args) {
        // 람다(식)
        // JDK 1.8부터 지원
        //  @FunctionalInterface를 생략 : BiFunction(매개변수 2개 지원)
        BiFunction<Integer, Integer, Integer> sum = (x,y)->{
            return x + y;
        };

        System.out.println(sum.apply(10,20));

    }
}
