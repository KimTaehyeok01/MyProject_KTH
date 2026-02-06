import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;


public class client1 {
    public static void main(String[] args) {
        Socket socket = new Socket();
        Scanner sc = new Scanner(System.in);
        int count = 0;

        try {
            socket.connect(new InetSocketAddress("192.168.0.2", 5001));
            System.out.println("서버 접속 성공!");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.print("1~100 사이 숫자 입력: ");
                int myNum = sc.nextInt();
                count++;

                dos.writeInt(myNum);
                dos.flush();

                String message = dis.readUTF();
                System.out.println("서버 답변: " + message);

                if (message.equals("정답입니다.")) {
                    System.out.println("총 시도횟수: " + count + "회");
                    break;
                }
            }
            socket.close();
        } catch (Exception e) {
            System.out.println("서버 접속 에러: " + e.getMessage());
        }
    }
}