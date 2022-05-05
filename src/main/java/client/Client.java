package client;


import java.net.ConnectException;
import java.net.Socket;
import java.util.Objects;
import lombok.NonNull;

public class Client {
    private final int port;
    private final String serverIp;

    public Client(@NonNull String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = validate(port);
    }

    private int validate(int port) {
        if (port <= 0) {
            throw new RuntimeException("정상적이지 않은 port 입니다.");
        }
        return port;
    }

    public void start() {
        try {
            Socket socket = new Socket(serverIp, port);

            System.out.println("서버에 연결되었습니다.");
            Thread sender = new Thread(Sender.create(socket.getOutputStream()));
            Thread receiver = new Thread(Receiver.create(socket.getInputStream()));

            sender.start();
            receiver.start();
        } catch (ConnectException ce) {
            throw new RuntimeException("서버 연결에 실패했습니다.", ce);
        } catch (Exception e) {
            throw new RuntimeException("서버 연결에 실패했습니다.");
        }
    }
}
