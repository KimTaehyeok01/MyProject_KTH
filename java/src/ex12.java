public class ex12 {
    public static void main(String[] args) {
        // 반복문 : for, while, do-while
        // JS문법과 유사

        // for문 패턴
        // for(초기식, 조건식, 증감식){
        //       실맹문;
        // }
        for(int i = 0; i < 5; i++){
            System.out.println("i: " + i);
        }

        // 무한 반복문 만드는 법
        for(;;){
            System.out.println("무한 반복문");
             break; // 무한루프 방지
        }
        //런타임 디버깅 사용법
        //*반복문에서 코드의 흐름을 파악하거나, 변수의 값을
        //확인하는 용도로 사용.
        //*print문만으로 전체 맥락을 파악하기 어려울때 사용.
        //실행 > 디버그 (Shift + F9) 기능 이용
        //Break Point(중단점)을 설정한다. CTRL + F8
        //스텝오버(F8) : 다음 줄로 이동
        //스텝인투(F7) : 함수 안으로 이동
        //다시시작(F9) : 계속해서 진행(Resume)
        //스텝아웃(SHIFT+F8) : 함수 밖으로 이동
    }
}
