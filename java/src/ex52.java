import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    String name;
    int kor;
    int eng;
    int math;

    public Student(String name, int kor, int eng, int math) {
        this.name = name;
        this.kor = kor;
        this.eng = eng;
        this.math = math;
    }
    void print(){
        int total = kor + eng + math;
        double avg = total / 3.0;
        System.out.printf("이름: %s 국어: %d 영어: %d 수학: %d 총점: %d 평균: %.1f\n",
                name, kor, eng, math, total, avg);
    }
}

public class ex52 {
    public static void main(String[] args) {
        //연습문제 - 성적 관리 프로그램
        // ArrayList 클래스 객체 배열을 사용해보자.
        //입력 및 출력 예시
        //-----------성적 관리 프로그램-------------
        //1.입력 2.전체출력 3.검색 4.수정 5.삭제 6.종료 : 1
        //이름 입력 : 홍길동
        //국어점수 입력 : 70
        //영어점수 입력 : 80
        //수학점수 입력 : 90
        //-----------성적 관리 프로그램-------------
        //1.입력 2.전체출력 3.검색 4.수정 5.삭제 6.종료 : 2
        //이름: 홍길동 국어: 70 영어: 80 수학: 90 총점: 240 평균: 80.0
        //-----------성적 관리 프로그램-------------
        //1.입력 2.전체출력 3.검색 4.수정 5.삭제 6.종료 : 3
        //이름 입력 :홍길동
        //이름: 홍길동 국어: 70 영어: 80 수학: 90 총점: 240 평균: 80.0
        //-----------성적 관리 프로그램-------------
        //1.입력 2.전체출력 3.검색 4.수정 5.삭제 6.종료 : 4
        //이름 입력 :홍길동
        //국어점수 입력 : 70
        //영어점수 입력 : 80
        //수학점수 입력 : 90
        //-----------성적 관리 프로그램-------------
        //1.입력 2.전체출력 3.검색 4.수정 5.삭제 6.종료 :5
        //이름 입력 :홍길동
        //홍길동 삭제됨.
        //-----------성적 관리 프로그램-------------
        //1.입력 2.전체출력 3.검색 4.수정 5.삭제 6.종료 :6
        //프로그램이 종료되었습니다.
        List<Student> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("-----------성적 관리 프로그램-------------");
            System.out.printf("1.입력 2.전체출력 3.검색 4.수정 5.삭제 6.종료 : ");
            int menu = sc.nextInt();

            if (menu == 1) {
                System.out.printf("이름 입력: ");
                String stdName = sc.next();

                System.out.printf("국어 입력: ");
                int kor = sc.nextInt();
                System.out.printf("수학 입력: ");
                int math = sc.nextInt();
                System.out.printf("영어 입력: ");
                int eng = sc.nextInt();
                list.add(new Student(stdName, kor, math, eng));
                // Student s = new Student(stdName, kor, math, eng);
                //                list.add(s); 이런식으로도 가능함.
                System.out.println(stdName + "님의 정보를 추가하였습니다.");
            }

            else if (menu == 2) {
                for (int i = 0; i < list.size(); i++) {
                    Student s = list.get(i);
                    s.print();
                }
            }

            else if (menu == 3) {
                System.out.printf("이름 검색: ");
                String stdName = sc.next();
                boolean found = false;

                for(Student s : list){
                    if(s.name.equals(stdName)){
                       s.print();
                    }
                }
                if (!found) {
                    System.out.println(stdName + "님을 찾을 수 없습니다.");
                }
            }

            else if (menu == 4) {
                System.out.printf("이름 검색: ");
                String stdName = sc.next();
                boolean found = false;

                for (int i = 0; i < list.size(); i++) {
                    Student s = list.get(i);
                    if (s.name.equals(stdName)) {
                        System.out.printf("국어 입력: ");
                        s.kor = sc.nextInt();
                        System.out.printf("수학 입력: ");
                        s.math = sc.nextInt();
                        System.out.printf("영어 입력: ");
                        s.eng = sc.nextInt();
                        found = true;
                        System.out.println(stdName +"님의 정보가 수정되었습니다.");
                        break;
                    }
                }
                if (!found) {
                    System.out.println(stdName + "님을 찾을 수 없습니다.");
                }
            }

            else if (menu == 5) {
                System.out.printf("이름 검색: ");
                String stdName = sc.next();
                boolean found = false;

                for (int i = 0; i < list.size(); i++) {
                    Student s = list.get(i);

                    if (s.name.equals(stdName)) {
                        list.remove(i);
                        found = true;
                        System.out.println(stdName + "님의 정보를 삭제하였습니다.");
                        break;
                    }
                }
                if (!found) {
                    System.out.println(stdName + "님을 찾을 수 없습니다.");
                }
            }

            else if(menu == 6){
                System.out.println("성적관리 프로그램을 종료합니다.");
                break;
            }

            else{
                System.out.println("잘못된 번호를 입력하셨습니다. 다시 입력하세요.");
            }
        }
        sc.close();

    }
}
