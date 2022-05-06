package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import repository.IpOutputStreamRepository;
import static util.IoUtil.createWriter;

public class Server {
    private final IpOutputStreamRepository ipRepository = new IpOutputStreamRepository();

    private final int port;

    public Server(int port) {
        this.port = validate(port);
    }

    private int validate(int port){
        if(port <= 0){
            throw new RuntimeException("정상적이지 않은 port 입니다.");
        }
        return port;
    }

    public void start() {
        Socket socket;
        Thread sender = new Thread(Sender.from(ipRepository));

        try( ServerSocket serverSocket = new ServerSocket(port) ) {
            System.out.println("서버가 시작되었습니다.");

            while(true) {
                socket = serverSocket.accept();
                System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속하였습니다.");

                ipRepository.put(socket.getInetAddress().getHostAddress(), createWriter(socket.getOutputStream()));
                System.out.println("현재 서버접속자 수는 "+ ipRepository.getSize()+"입니다.");

                Thread receiver = new Thread(Receiver.create(socket, ipRepository));

                receiver.start();
                sender.start();
            }
        } catch(IOException e) {
            throw new RuntimeException("socket 생성에 실패 했습니다.", e);
        }
    }
}