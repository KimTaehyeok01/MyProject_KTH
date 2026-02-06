import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        Socket socket = new Socket();
        Scanner sc = new Scanner(System.in);
        int count = 0;

        try {
            socket.connect(new InetSocketAddress("192.168.0.61", 5001));
            System.out.println("서버 접속 성공!");

            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            while (true) {
                System.out.print("1~100 사이 숫자 입력: ");
                String myNum = sc.next();
                count++;

                byte[] bytes = myNum.getBytes("UTF-8");
                os.write(bytes);
                os.flush();

                bytes = new byte[1024];
                int readByteCount = is.read(bytes);
                String message = new String(bytes, 0, readByteCount, "UTF-8");
                System.out.println("서버 답변: " + message);

                if (message.equals("정답입니다.")) {
                    System.out.println("총 시도횟수: " + count + "회");
                    break;
                }
            }
            socket.close();
        }

        catch (Exception e) {
            System.out.println("서버 접속 에러!");
        }
    }
}