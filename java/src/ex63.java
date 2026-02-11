import java.util.Optional;

public class ex63 {
    public static void main(String[] args) {
        // Optional 클래스
        //       : JDK 1.8부터 지원
        //       : null값 체크 확인을 위해 사용
        //       : NullPointerException 발생 빈도를 줄이고자 만듦.
        String nullValue = null;

        // 안전하지 않은 코드(견고한 코드가 아니다)
//        System.out.println(nullValue.toLowerCase());

        // 기존의 널 체크 방법
        if (nullValue != null) {
            System.out.println(nullValue.toLowerCase());
        }

        nullValue = "Abc";
        // 널일 수도 있는 값(객체)로 Optional 객체를 생성한다.
        Optional optional1 = Optional.ofNullable(nullValue);
        System.out.println(optional1.isPresent()); // isPresent()는 널인지 아닌지 확인하는 메서드
        System.out.println(optional1.isEmpty());

        if (optional1.isPresent()) {
            System.out.println(optional1.get());
        }

        String notNull = "123";
        // of함수 : 반드시 널이 아닌 값으로만 초기화 가능.
        Optional optional2 = Optional.of(notNull);

        // orElse 함수 : null일 경우 대체 값을 설정한다.
        String strResult = Optional.ofNullable(nullValue).orElse("널입니다.");

        // map()함수
        nullValue = null;
        String strResult2 = Optional.ofNullable(nullValue).map(String::toUpperCase).orElse("대문자로 변환 실패함");
        System.out.println(strResult2);

        nullValue = "abc";
        String strResult21 = Optional.ofNullable(nullValue).map(String::toUpperCase).orElse("대문자로 변환 실패함");
        System.out.println(strResult21);

        // filter()함수
        String strResult3 = Optional.ofNullable("123").
                // 람다가 true를 반환하면, 원래 가진 값 '123'을 반환하고, false를 반환하면 orElse인 '12값이 없음'를 반환함.
                filter(val -> val.contains(("12"))).
                orElse("12값이 없음");

        System.out.println(strResult3);
    }
}
