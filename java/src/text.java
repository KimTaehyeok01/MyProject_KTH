import java.io.FileReader;
import java.util.Scanner;

public class text {
    public static void main(String[] args) {
        try (FileReader reader = new FileReader("score.txt");
            Scanner fileSc = new Scanner(reader);
            Scanner inputSc = new Scanner(System.in)){

        System.out.print("학생 이름과 과목을 입력: ");
        String stdName = inputSc.next();
        String searchSub = inputSc.next();

        while(true){
            String line = fileSc.nextLine();
            String [] data = line.split(" ");

            if(data[0].equals(stdName)){
                int score = 0;

                switch (searchSub){
                    case "영어" : score = Integer.parseInt(data[1]); break;
                    case "수학" : score = Integer.parseInt(data[2]); break;
                    case "국어" : score = Integer.parseInt(data[3]); break;
                    default:
                        System.out.println("잘못된 과목입니다.");
                        return;
                }
                System.out.println(stdName +"의 " +searchSub +"점수는 " +score+ "입니다.");
                break;
            }
                }


        }
         catch(Exception e){
            e.printStackTrace();
        }

}
}
