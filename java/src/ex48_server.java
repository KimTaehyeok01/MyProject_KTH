//자바에서는 .java파일이름과 클래스이름이 동일해야 됨.
//리팩토링 -> 참조하는 모든 코드를 검색해서, 일괄 변경.

//인터넷 통신
//1. TCP/IP(Socket통신) : 게임, 채팅에 주로 사용됨.
//        : 속도가 빠르다.
//        : 연결지향(한번 접속하면, 계속 연결이 지속된다.)
//        : 비공개 포트(Port)방식
//        : 공식 포트 80 HTTP, 21 FTP, 443 HTTPS
//2. HTTP통신 : 대부분의 웹앱(web,app)에서 사용
//        : 웹브라우저 서비스 대응 용도
//3. SMTP,POP(이메일),TELNET(원격접속),SSH(보안원격접속)
// TCP : 연결지향, 데이터 체크 기능(체크섬)
// UDP : 비연결지향, 방송용 - 데이터체크 안함.

//클라이언트(서비스를 제공받으면) <-----> 서버(서비스 제공)

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ex48_server {
    public static void main(String[] args) {
        //C언어(제대로된 Lib제공x) -> Java
        //통신,보안 미비           -> JDK를 제공(종합선물세트)
        try {
            ServerSocket serverSocket = new ServerSocket();
            //localhost(127.0.0.1) : 내 PC 주소(IP)
            //5001 : ex48_server프로그램의 주소(Port)
            //클라가 접속하기를 무한대기한다. 동기(Sync)방식.
            serverSocket.bind(new InetSocketAddress("localhost", 5001));
            while (true) { //무한루프
                System.out.println("서버가 연결을 기다리는 중...");
                Socket socket = serverSocket.accept();  //동기(Sync), 무한대기중...
                InetSocketAddress isa =
                        (InetSocketAddress)socket.getRemoteSocketAddress();
                System.out.println("서버가 연결을 수락함:"+isa.getHostName());

                //클라이언트의 데이터를 수신한다.
                byte[] bytes = null;
                String message = null;
                InputStream is = socket.getInputStream();
                bytes = new byte[1024]; //1024바이트만큼 버퍼 생성
                int readByteCount = is.read(bytes);
                message = new String(bytes, 0, readByteCount, "UTF-8");
                System.out.println("서버가 데이터받기 성공함.");
                System.out.println("서버가 받은 데이터:" + message);

                //서버가 클라이언트에 데이터 보내기
                OutputStream os = socket.getOutputStream();
                message = "Hi~ This is Server~";
                bytes = message.getBytes("UTF-8");
                os.write( bytes );
                os.flush();
                System.out.println("서버가 클라에게 데이터보내기 성공!");

                is.close();
                os.close();
                socket.close(); //정리(메모리), TCP통신(통신종료 알림),
                //입출력스트림(Stream-물줄기) 종료
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}