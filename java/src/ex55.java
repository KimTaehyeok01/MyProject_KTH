import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class ex55 {
    public static void main(String[] args) {
        // LocalDate, LocalTime, LocalDateTime
        // JDK 1.8부터 지원
        // 타임존 개념이 없는 단순 날짜/시간 정보를 출력
        // 오픈소스 JodaTime 클래스를 본따서 만듦.
        // 최신 트렌드는 Date보다는 LocalDate, LocalTime, LocalDateTime

        //Date,Calender의 단점
        //1. get/set함수로 인해서 값이 중간에 변경될 수 있다.
        //2. 1월을 0으로 표현하는 문제
        //3. 년/월/일 계산은 Date로만 안되고 Calendar를 사용해야 됨.

        // LocalDate
        LocalDate localDate = LocalDate.now();
        System.out.println("now: " + localDate);

        // 날짜 설정
        LocalDate birthday = LocalDate.of(2000, 12, 1);
        System.out.println("birthday: " + birthday);

        // 문자열로 날짜 변경(yyyy-mm-dd)
        LocalDate xmax = LocalDate.parse("2026-12-25");
        System.out.println("크리스마스: " + xmax);

        // 안되는 표현 방식
        // 2026/12/25 2026.12.25 2026 12 25 20261225 2026:12:25 -> 오직 2026-12-25만 가능
        LocalDate xmax1 = LocalDate.parse("2026-12-25");
        System.out.println("크리스마스: " + xmax1);

        // LocalTime
        LocalTime nowTime = LocalTime.now();
        System.out.println("nowTime: " + nowTime);

        // 세계시간 - 프랑스
        LocalTime parisTime = LocalTime.now(ZoneId.of("Europe/Paris"));
        System.out.println("parisTime: " + parisTime);

        // 세계시간 - 한국
        LocalTime korea = LocalTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println("parisTime: " + korea);

        //시간 설정하기
        LocalTime sleepTime = LocalTime.of(23,30,0);
        System.out.println(sleepTime);

        // 시간 더하기/빼기
        LocalTime getupTime = sleepTime.plusHours(8);
        System.out.println(getupTime);
        LocalTime inHours = sleepTime.minusHours(5);
        System.out.println(inHours);

    }
}
