import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

// 192.168.0.61

public class ex48_client {
    public static void main(String[] args) {
        Socket socket = new Socket();
        try {
            //접속하려는 서버의 주소(IP,Port)
            socket.connect(new InetSocketAddress("localhost", 5001));
            System.out.println("서버 접속 성공!");

            //서버에 문자열형으로 데이터를 보내보자
            byte[] bytes = null;
            String message = null;

            OutputStream os = socket.getOutputStream();
            message = "Hi! This is Client~";
            //인터넷에서 UTF-8(유니코드)를 지원하지 않는
            //기기를 통과해야 되므로, Byte단위로 쪼개서 전송한다.
            bytes = message.getBytes("UTF-8");
            os.write( bytes );
            os.flush(); //버퍼(Buffer:완충기능의 여유메모리)
            //버퍼에 남아있는 데이터를 다 내보내기

            //서버로부터 데이터 받기
            InputStream is = socket.getInputStream();
            bytes = new byte[1024];
            int readByteCount = is.read(bytes);
            message = new String(bytes, 0, readByteCount, "UTF-8");
            System.out.println("클라가 서버로부터 데이터수신 성공!");
            System.out.println("클라가 서버로부터 받은 데이터:" + message);

            is.close();
            os.close(); //마무리(버퍼 비우기, 전송 끝)
        }
        catch (Exception e) {
            System.out.println("서버 접속 에러!");
        }
    }
}