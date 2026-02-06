import java.io.FileReader;
import java.util.Scanner;

public class ex47 {
    public static void main(String[] args) {
        // 연습문제
        // 철수는 초등학교 교사이다.
        // 학생 영희, 수만, 순신의 성적관리 프로그램을 작성하고자 한다.
        // 학생들의 영어, 수학, 국어 점수를 각각 입력받고,
        // score.text에 저장하고, 읽어오는 프로그램을 작성해보자.
        // 파일형식
        // 이름 영어 수학 국어
        // 영희 100 80 70 \n
        // 수만 70 90 70 \n
        // 순신 100 90 70 \n

        // 그 다음 학생이름과 과목을 Scanner로 입력받고,
        // 점수를 출력하는 프로그램을 작성하시오.
        // 입력 예) 영희 영어
        // 출력 예) 100

        //FileReader : 바이트 단위로 읽어오기
        //BufferedReader : \n문자까지 한줄 읽어오기

        try (FileReader reader = new FileReader("score.txt");
             Scanner fileSc = new Scanner(reader);
             Scanner inputSc = new Scanner(System.in)) {

            System.out.print("학생 이름과 과목을 입력: ");
            String StdName = inputSc.next();
            String searchSub = inputSc.next();

            while (true) {
                String line = fileSc.nextLine();
                String[] data = line.split(" ");

                if (data[0].equals(StdName)) {
                    int score = 0;

                    switch (searchSub) {
                        case "영어": score = Integer.parseInt(data[1]); break;
                        case "수학": score = Integer.parseInt(data[2]); break;
                        case "국어": score = Integer.parseInt(data[3]); break;
                        default:
                            System.out.println("없는 과목입니다.");
                            return;
                    }

                    System.out.println(StdName + "의 " + searchSub + " 점수는: " + score);
                    break;
                }

            }

        }
        catch (Exception e) {
            System.out.println("에러: " + e.getMessage());
        }




    }
}
// Exception 자주 발생하는 곳
// 1. 파일 처리할 때
// 2. 통신
// 3. 형변환(10진수 -> 2진수)
