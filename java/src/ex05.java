import java.util.Arrays;

public class ex05 {
    public static void main(String[] args) {
        // 문자열 관련 함수들
        String str1 = "hello java!";
        String str2 = "안녕하세요! 반갑습니다.";

        System.out.println(str1.length());
        System.out.println(str2.length());

        char c1 = str1.charAt(0);
        char c2 = str1.charAt(1);
        System.out.printf("c1: %c\n" , c1);
        System.out.println("c2: " + c2);

        // 문자열 위치(인덱스 찾기)
        System.out.println(str1.indexOf("java"));
        System.out.println(str1.lastIndexOf("!"));

        String str3 = "Java Study";
        // 대문자 : toUpperCase()
        System.out.println(str3.toUpperCase());
        // 소문자 : toLowerCase()
        System.out.println(str3.toLowerCase());
        // 문자열 검색시 : indexOf()
        System.out.println(str3.toLowerCase().indexOf("ava"));
        // 문자열 치환하기 : replace()
        System.out.println(str3.replace("Study", "공부"));
        // 문자열 일부 가져오기 : subString()
        System.out.println(str3.substring(0, 4)); // 시작 인덱스, 끝 인덱스(+1)
        // 문자열을 배열로 가져오기 - JS에선 spilt()함수
        String [] strArray = "abc/def-ghi jki".split("/|-|");
        System.out.println(Arrays.toString(strArray)); // Arrays.toString()
        // 양쪽 공백제거 : trim()
        System.out.println(" abc ".trim());
        System.out.println(" abc def ".trim());
        // 모든 공백제거 : replace(), replaceAll()
        System.out.println(" abc def ".replace(" ",""));
        // 문자열 연결함수 : concat()
        System.out.println("abc".concat("123"));
        // 문자열을 포함하는지 true/false로 반환하는 함수
        System.out.println("abc123".contains("123"));

        // 문자열 비교할 때
        String str4 = "java";
        String str5 = "java";
        // 아래 코드는 주소값을 가진 정수를 비교하게 되므로 안됨
        System.out.println(str4 == str5);
        // 해결법 : equals()
        System.out.println(str4.equals(str5));

        String str6 = "abc";
        String str7 = new String("abc");
    }
}












