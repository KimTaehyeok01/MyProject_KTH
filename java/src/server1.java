import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class server1 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("192.168.0.2", 5001));

            int answer = (int) (Math.random() * 100) + 1;
            System.out.println("정답 설정 완료: " + answer);

            while (true) {
                System.out.println("클라이언트 접속 대기 중...");
                Socket socket = serverSocket.accept();

                DataInputStream dis =
                        new DataInputStream(socket.getInputStream());
                DataOutputStream dos =
                        new DataOutputStream(socket.getOutputStream());

                while (true) {

                    int clientNum = dis.readInt();
                    System.out.println("클라이언트가 보낸 수: " + clientNum);

                    String response;
                    if (clientNum < answer) response = "더 큰 수입니다.";
                    else if (clientNum > answer) response = "더 작은 수입니다.";
                    else response = "정답입니다.";

                    dos.writeUTF(response);
                    dos.flush();

                    if (response.equals("정답입니다.")) break;
                }
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}