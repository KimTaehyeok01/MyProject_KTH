import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ex57 {
    public static void main(String[] args) {
        // 스택과 큐
        // 스택(Stack) : LIFO
        //            : 맨 나중에 들어간 요소가 먼저 나오는 자료구조
        //            : 용도) 함수 호출시 이전 함수의 구조, 데이터를 보관
        // 큐(Queue) : FIFO
        //            : 맨처음 들어간 요소가 맨 처음 나오는 자료구조
        //            : 용도) 버퍼(Buffer). 입력속도와 출력속도가 다른 경우 완충 역할을 한다.
        System.out.println("=======Stack========");
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println(stack);
        // stack.pop();
        System.out.println(stack.pop()); // 맨 마지막 요소를 반환하면서 지움
        System.out.println(stack);
        System.out.println(stack.peek()); // 최상단(맨 마지막에 있는 요소)에 있는 요소를 반환. 지우는 건 x
        System.out.println(stack);
        System.out.println(stack.contains(20));
        System.out.println(stack.size());
        System.out.println(stack.isEmpty());

        // LinkedList가 Queue를 상속받았으므로 업캐스팅
        System.out.println("=======Queue========");
        Queue<Integer> queue = new LinkedList<>(); // Queue는 Stack과 다르게 LinkedList에서 가져와 생성한다.
        queue.add(10);
        queue.add(20);
        queue.add(30);
        System.out.println(queue);

        queue.offer(40); // offer는 맨 뒤에 추가
        System.out.println(queue);

        // LinkedList는 가변용량이라서 용량제한이 거의 없다.
        // add : 실패시 Exception 발생(반드시 추가될 것으로 알고 설계)
        // offer : 실패시 false 발생(실패를 가정하고 설계)

        // 제일 먼저 들어간 값을 제거하고, 그 값을 반환한다.
        System.out.println(queue.poll());
        System.out.println(queue);

        // 제일 먼저 들어간 값을 반환한다. 제거는 x
        System.out.println(queue.peek());
        System.out.println(queue);
    }
}
