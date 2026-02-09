// 제네릭(Generic)
//      : 자바가 정적언어이기 때문에, 타입 호환성이 엄격하다.
//        그러나 형(Type)에 따른 데이터 전송을 편하기 하기 위해
//        가변적인 타입선언을 할 수 있도록 해줌. (다형성-상속과 상관없음)
//      : JDK 1.5부더 지원
class Keyboard1 { // 다형성을 이용한 유연한 타입 지원
    // 최상위 클래스 Object를 이용하여
    // 모든 객체를 담을 수 있다.
    // 단점: 다운캐스팅(강제형변환)해야 함.
    private Object object;

    // Getter/Setter 자동생성

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

// T는 타입을 가르키는 심볼.
class Keyboard2<T> { // 제네릭을 이용한 유연한 타입 지원
    private T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}

class Keyboard3<Integer> { // 제네릭을 이용한 유연한 타입 지원
    private Integer object;

    public Integer getObject() {
        return object;
    }

    public void setObject(Integer object) {
        this.object = object;
    }
}

public class ex53 {
    public static void main(String[] args) {

        Keyboard1 kb1 = new Keyboard1();
        kb1.setObject(new String("키보드1")); // kb1.setObject("키보드"); 같은 말임. -> 업캐스팅
        String str1 = (String) kb1.getObject();
        System.out.println(str1);

        Keyboard2<Integer> kb2 = new Keyboard2<>(); // 객체 선언시 타입을 결정한다.
        kb2.setObject(10);
        Integer intVal = kb2.getObject(); // 다운캐스팅 안해도 됨.
        System.out.println(intVal);

        Keyboard2<String> kb3 = new Keyboard2<>(); // 객체 선언시 타입을 결정한다.
        kb3.setObject("문자열");
        String intVal1 = kb3.getObject(); // 다운캐스팅 안해도 됨.
        System.out.println(intVal1);
    }
}
