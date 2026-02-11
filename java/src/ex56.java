import java.util.LinkedList;

public class ex56 {
    public static void main(String[] args) {
        // 컬렉션 프레임워크(List, Map, Set)
        // List - ArrayList, LinkedList
        // List 인터페이스 - ArrayList, LinkedList
        // LinkedList
        //       : ArrayList와 사용법은 유사
        //       : 내부적으로 처리하는 방법이 다름
        //       : 다음 요소의 주소값을 이전 요소가 가지고 있음.
        //       : 흩어진 데이터를 처리할 때 유리
        LinkedList<Integer> nums = new LinkedList<>();
        nums.add(10);
        nums.add(30);
        System.out.println(nums);
        nums.add(1, 20);
        System.out.println(nums);

        // 차별화된 함수
        System.out.println(nums.getFirst());
        System.out.println(nums.getLast());

        // 자료구조(알고리즘)에서 주로 사용하는 함수들
        System.out.println(nums.peek()); // 처음 요소를 반환
        System.out.println(nums);
        System.out.println(nums.poll()); // 처음요소를 반환 후 삭제
        System.out.println(nums);
        nums.push(50);
        System.out.println(nums); // add(0, 추가할값)이랑 같음 맨 왼쪽에 요소를 추가
        System.out.println(nums.pop());
        System.out.println(nums); // 맨 왼쪽에 값을 지운 값 반환

        // poll : 관점 Queue FIFO, 빈 리스트에서 실행하면 null 반환
        // pop : 관점 Stack LIFO, 빈 리스트에서 실행하면 Exception 발생


    }
}
