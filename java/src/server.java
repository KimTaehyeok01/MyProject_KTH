import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("192.168.0.61", 5001));

            int answer = (int) (Math.random() * 100) + 1;
            System.out.println("정답 설정 완료: " + answer);

            while (true) {
                System.out.println("클라이언트 접속 대기 중...");
                Socket socket = serverSocket.accept();

                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();

                while (true) {
                    byte[] bytes = new byte[1024];
                    int readByteCount = is.read(bytes);
                    if(readByteCount == -1) break;

                    String clientMsg = new String(bytes, 0, readByteCount, "UTF-8");
                    int clientNum = Integer.parseInt(clientMsg.trim()); // 보내준 parseInt 사용!
                    System.out.println("클라이언트가 보낸 수: " + clientNum);

                    String response;
                    if (clientNum < answer) response = "더 큰 수입니다.";
                    else if (clientNum > answer) response = "더 작은 수입니다.";
                    else response = "정답입니다.";

                    bytes = response.getBytes("UTF-8");
                    os.write(bytes);
                    os.flush();

                    if (response.equals("정답입니다.")) break;
                }
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}