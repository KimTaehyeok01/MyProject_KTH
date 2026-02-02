public class ex07 {
    public static void main(String[] args) {
        // 산술 연산자
        int i = 10;
        int j = 3;
        System.out.println(i + j);
        System.out.println(i - j);
        System.out.println(i * j);
        System.out.println(i * j);
        System.out.println(i / (float)j); // int / float = float
        System.out.printf("%.2f\n", i / (float)j);
        System.out.println(i % j);

        // 소수점 없애기
        float pi = 3.54f;
        // 1. Math.floor
        System.out.println(Math.floor(pi)); // 3.0
        System.out.println(Math.round(pi)); // 4
        System.out.println(Math.round(Math.floor(pi))); // 3
        // 2. 형변환
        System.out.println((int)pi); // 3

        int n = 123;
        double d = 3.567;
        //1. 일의 자릿수 3을 출력하시오.
        System.out.println(n % 10);
        //2. 십의 자릿수 2를 출력하시오.
        System.out.println((n / 10)%10);
        //3. 백의 자릿수 1을 출력하시오.
        System.out.println((n / 100));
        //4. 소숫점 첫째자리를 출력하시오.
        //출력예) 5
        System.out.println(Math.round(Math.floor((d * 10)% 10)));
        System.out.println("d: " +(int)(d* 10)%10);
        //5. 소숫점 첫째자리에서 반올림하여 출력하시오.
        //출력예) 4.0
        System.out.println(Math.round(d % 10));
        System.out.println((int) (d+0.5));
        //6. 소숫점 둘째자리에서 반올림하여 출력하시오.
        //출력예) 3.6
        System.out.println(Math.round(d*10) /(float)10);

        // Math.floor 소숫점 첫째자리 까지 내림
        // 예) 3.1 ~ 3.99 => 3.0
        // 예) 3.1 ~ 3.99 => -4








    }
}
