import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ex54 {
    public static void main(String[] args) {
        // 미팅 약속
        // 어디서? 위치 - GPS좌표(위도, 경도)
        //            - 행정주소(서울시 중구 충정로 123로 10번지)
        //            구글 Geocoding API(주소 <-> GPS좌표)
        // 언제? 날짜/시간

        // 날짜와 시간 클래스
        // 1. Date : 약한 수준의 기능 제공
        // 2. Calendar : 높은 수준의 기능 제공. 내부 달력이 있음.
        //               +- 2억 9천만년 범위. 윤년
        //               지구의 공전 주기 약 365.2422일
        // 양력 달력 - 전세계 표준 (단 하나, 태국 캘린더)
        // 한국시간 = 세계 표준시(GMT) +9시간
        Date date = new Date(); // 현재시간
        System.out.println("date: " + date);

        // 유닉스 타임스탬프(time Stamp)
        // 1970년 01월 01일 0시 0분 0초 00밀리초부터 시작된 밀리초 단위의 정수.
        // 예) 시간은 단위가 많다. 년월일시분초밀리초
        //     타임스탬프는 정수 한개다
        long mills = date.getTime();
        System.out.println("mills: " + mills);

        // 날짜시간 포맷(형식화)
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd H:mm:ss"); // HH:24시간제, hh:12시간제
        System.out.println(sdf1.format(date));

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss"); // a는 오전 오후를 의미함.
        System.out.println(sdf2.format(date));

        // 캘린더 클래스
        Calendar cal = new GregorianCalendar(Locale.KOREA);
        // Date객체 가져오기
        System.out.println(cal.getTime());
        // 타임스탬프 값 가져오기
        System.out.println(cal.getTimeInMillis());

        // 년도
        System.out.println(cal.get(Calendar.YEAR));
        // 달 : 1월은 0이다. 12월은 11이다.
        System.out.println(cal.get(Calendar.MONTH));
        // 일
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        // 요일 : 1일요일, 2월요일,.. 7토요일
        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
        // 이달의 몆주차인가?
        System.out.println(cal.get(Calendar.WEEK_OF_MONTH));
        // 올해의 몆주차인가?
        System.out.println(cal.get(Calendar.WEEK_OF_YEAR));
        // 시간 : 24시간제
        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
        // 시간 : 12시간제
        System.out.println(cal.get(Calendar.HOUR));
        // 오전/오후 : 0 AM / 1 PM
        System.out.println(cal.get(Calendar.AM_PM));
        // 분
        System.out.println(cal.get(Calendar.MINUTE));
        // 초
        System.out.println(cal.get(Calendar.SECOND));
        // 밀리초
        System.out.println(cal.get(Calendar.MILLISECOND));

        // 전후 시간 구하기
        // 예) 1달후 무료회원 마감일 구하기
        // 10시간 후
        cal.add(Calendar.HOUR, 10);
        printTime(cal);

        // 10시간 전
        cal.add(Calendar.HOUR, -10);
        printTime(cal);

        // 3달 후
        cal.add(Calendar.MONTH, 1);
        printTime(cal);

        // 1년 후
        cal.add(Calendar.YEAR, 3);
        printTime(cal);
    }

    static void printTime(Calendar cal){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sf.format(cal.getTime());
        System.out.println(str);
    }
}
