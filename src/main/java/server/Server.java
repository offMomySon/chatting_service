package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;
import repository.IpOutputStreamRepository;
import server.domain.IpAddress;
import static util.IoUtil.createWriter;

@Slf4j
public class Server {
    private final IpOutputStreamRepository ipRepository = new IpOutputStreamRepository();
    private final int port;

    public Server(int port) {
        this.port = validate(port);
    }

    private static int validate(int port){
        if(port <= 0){
            throw new RuntimeException("Abnormal port number.");
        }
        return port;
    }

    public void start() {
        Socket socket;
        Thread sender = new Thread(
            () -> Sender.from(ipRepository)
                .waitAndThenSendMsg()
        );
        sender.start();

        try( ServerSocket serverSocket = new ServerSocket(port) ) {
            log.info("Server start.");

            while(true) {
                socket = serverSocket.accept();
                log.info("[{} : {}] is connected.", socket.getInetAddress(), socket.getPort());

                ipRepository.put(IpAddress.create(socket.getInetAddress().getHostAddress()), createWriter(socket.getOutputStream()));
                log.info("Current user count : {}", ipRepository.getSize());

                Socket finalSocket = socket;
                Thread receiver = new Thread(
                    ()-> Receiver.create(finalSocket, ipRepository)
                        .waitAndThenGetMsg()
                );
                receiver.start();
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail connection.", e);
        }
    }
}