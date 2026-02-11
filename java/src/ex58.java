import java.util.*;

public class ex58 {
    public static void main(String[] args) {
        // Map(맵) : Key와 Value로 구성된 데이터구조
        //         : Key(문자열타입) - Value(객체, 기본 데이터 타입)
        //         : Json, XML 데이터 연결이 쉽다.
        //         : -> 클래스도 Json/XML과 오브젝트 매핑을 할 수 있다.
        //         : 데이터 바인딩 또는 직렬화(Serialization)
        //         : 자바 객체 -> Json/XML문자열 (직렬화)
        //         : Json/XML문자열 -> 자바 객체(역직렬화)

        // List가 ArrayList의 인터페이스의 구현체이므로, 업캐스팅된다.
        List<String> list = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();

        // Map이 HashMap의 부모클래스이므로 업캐스팅된다.
        Map<String, String> map = new HashMap<>(); // <>에 2개가 들어감. 왼쪽은 무조건 String(문자열)이어야 함.
        HashMap<String, String> hashMap = new HashMap<>(); // 맥락은 위에랑 같음. 위에 코드를 더 많이 씀.

        map.put("username", "hong");
        map.put("password", "1234");
        System.out.println(map);

        // 키로 값을 얻어온다.
        System.out.println(map.get("username"));
        System.out.println(map.get("password"));

        // 전체순회
        // 일반 for문은 인덱스가 없어서 순회 불가. Map과 Set은 인덱스가 없기 때문이다.

        // 향상된 for문
        Set<String> keys = map.keySet();
        System.out.println(keys);
        for(String key : keys){
            System.out.println("값: "+map.get(key));
        }

        // 이터레이터 이용
        Iterator<String> it = keys.iterator();
        while (it.hasNext()){
            String key = it.next();
            System.out.println(map.get(key));
        }
    }
}




























