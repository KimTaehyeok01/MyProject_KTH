import java.util.ArrayList;
import java.util.Collections;

public class ex50 {
    public static void main(String[] args) {
        // 데이터 구조 : 데이터를 담는 그릇
        // 1. 변수 : 단 하나의 값(숫자, 문자열(문자), 논리)
        // 2. 배열 : 인덱스가 있는 연속된 공간에 담음(Java 배열은 동일한 타입이어야 함)
        // 3. 리스트 : 인덱스가 있는 연속된 공간.  추가/삭제/교체가 간편하다.
        // 4. Map : Key-Value(Json) 형태의 데이터 구조. 인덱스(순서)가 없음.
        // 5. Set : 집합구조의 데이터 구조(중복없음). 인덱스(순서)가 없음.
        // 6. 스택/큐 : Stack/Queue 데이터 구조 LIFO/FIFO
        // * 클래스 : 변수와 함수의 모음. 객체.

        // 컬렉션 프레임워크 : List(=list), Map(=KV객체), Set(집합 = 중복제거)
        // 기존 자료구조에 없는 데이터 구조를 추가 해놓은 것.

        // 1. 리스트(List)
        // List 인터페이스를 구현해서 ArrayList(일반), LinkedList(알고리즘기반)
        // 클래스가 있다.
        // ArrayList : 순차적으로 데이터가 나열되어 있다. 순차적인 데이터 접근에 용이하고 빠르다.
        // LinkedList : 다음 요소의 주소값을 이전 요소가 가지고 있다. 흩어져있는 데이터에 대한 접근이 용이하다.
        //    <>는 제네릭(타입을 확정해준다)        <>뒤에 들어가는 타입은 생략 가능.
        ArrayList<String> fruits = new ArrayList<String>(); // 빈 리스트
        // ArrayList<String> fruits = new ArrayList<>();
        System.out.println(fruits);

        // add() : 맨 뒤에다가 요소를 추가함.
        fruits.add("수박");
        System.out.println(fruits);
        fruits.add("망고");
        System.out.println(fruits);
        fruits.add("딸기");
        System.out.println(fruits);

        // 리스트 길이를 알아보자.
        System.out.println(fruits.toArray().length);
        System.out.println(fruits.size());
        fruits.add(0, "레몬");
        System.out.println(fruits);
        fruits.add(1, "사과");
        System.out.println(fruits);

        // remove() : 삭제할 인덱스를 써주면 삭제가 된다.
        fruits.remove(1);
        System.out.println(fruits);

        // 오름차순 정렬
        Collections.sort(fruits);
        System.out.println(fruits);

        // 내림차순
        Collections.sort(fruits, Collections.reverseOrder());
        System.out.println(fruits);

        // 요소의 변경/치환
        fruits.set(1, "복숭아");
        System.out.println(fruits);

        // 전체지우기
        fruits.clear();
        System.out.println(fruits);
    }
}
