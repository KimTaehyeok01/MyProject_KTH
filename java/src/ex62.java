import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ex62 {
    public static void main(String[] args) {
        // Stream 계열의 함수
        //   : 알고리즘(코테)시에 복잡하지만 강력한 기능
        //   : JS 배열.map() reduce() filter()

        // filter(조건 필터링)
        List<Integer> nums = Arrays.asList(1, 15, 8, 20, 5, 30);
        // 10보다 큰 숫자만 리스트로 만들기
        List<Integer> result = nums.stream().filter(n -> n > 10).collect(Collectors.toList());
//                                           filter((n)-> {return (n>10);}) ==  filter(n -> n > 10) 같은 것임.

        System.out.println(result);

        // map(데이터 변형)
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        // 단어를 대문자로 변환하기
        List<String> upperWords = words.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(upperWords);

        // reduce(결과를 하나의 값으로 응축)
        List<Integer> vals = Arrays.asList(1, 2, 3, 4, 5);
        int valsResult = vals.stream().reduce(0, (a, b) -> a + b);

        System.out.println(valsResult);

        // sort 정렬
        List<String> names =Arrays.asList("이순신", "강감찬", "을지문덕");
        // 가나다순 정렬
        List<String> sorted = names.stream().sorted().collect(Collectors.toList());
        System.out.println(sorted);

    }
}
