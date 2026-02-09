import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ex51 {
    public static void main(String[] args) {
        // ArrayList 사용 예
        // ArrayList는 가변용량(String동일) - 넣은만큼 들어간다.

        // ArrayList의 다양한 선언 방식
        ArrayList<Integer> num1 = new ArrayList<Integer>(); // 앞 뒤로 타입을 명시
        ArrayList<Integer> num2 = new ArrayList<>(); // 타입 생략 -> 이걸 더 많이 사용(더 안써도 되니까)
        List<Integer> num10 = new ArrayList<>();

        // 10개의 길이(공간)가 생긴다. -> 10개까지 넣을 수 있다.
        //Capacity(용량) : 물리적을 확보된 메모리 공간
        // 리사이징 과정을 생략하므로 성능 향상
        ArrayList<Integer> num3 = new ArrayList<>(10);

        // 다른 ArrayList로 초기화하면서 선언.
        ArrayList<Integer> num4 = new ArrayList<>(num1);

        // 데이터를 선언과 동시에 데이터를 바로 줌.
        ArrayList<Integer> num5 = new ArrayList<>(
                Arrays.asList(10, 20, 30, 40)
        );
        System.out.println(num5);

        // 전체리스트 순회
        // for문
        for(int i = 0; i < num5.toArray().length; i++){
            System.out.println(num5.get(i));
        }

        // 향상된 for문
        for(Integer num : num5){
            System.out.println(num);
        }

        // 이러레이터(Iterator) : 열거자
        // 콜렉션(Collections)의 순차적인 처리를 도와주는 클래스
        // hasNext() : 다음 요소를 가지고 있는지 T/F로 반환
        // next() : 다음 요소를 반환함.(it카운터++)
        Iterator<Integer> it = num5.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }

        // 값을 포함하는지 T/F로 반환
        if(num5.contains(30)){
            System.out.println("30이 있다.");
        }else  System.out.println("30이 없다.");

        // 특정요소에 인덱스 가져오기
        System.out.println(num5.indexOf(40));

    }
}
