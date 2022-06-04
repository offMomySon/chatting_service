package server;


import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.writer.file.FileWriter;
import server.writer.smf.SmfSender;

@Slf4j
public class Server {
    private static final int MIN_PORT_NUM = 7777;

    private final SmfSender smfSender;
    private final FileWriter fileWriter;
    private final int port;

    public Server(@NonNull SmfSender smfSender, @NonNull FileWriter fileWriter, int port) {
        this.smfSender = smfSender;
        this.fileWriter = fileWriter;
        this.port = validate(port);
    }

    private static int validate(int port){
        if(port < MIN_PORT_NUM){
            throw new RuntimeException("Abnormal port number.");
        }
        return port;
    }

    public void start() {
        Socket socket;
        Thread sender = new Thread(
            () -> Sender.create(smfSender, fileWriter, System.in).waitAndThenSendMsg()
        );
        sender.start();

        try( ServerSocket serverSocket = new ServerSocket(port) ) {
            log.info("Server start.");

            while(true) {
                socket = serverSocket.accept();
                log.info("[{} : {}] is connected.", socket.getInetAddress(), socket.getPort());

                Address address = new Address(socket.getInetAddress().getHostAddress());
                OutputStream socketStream = socket.getOutputStream();

                smfSender.addAddress(address, socketStream);
                fileWriter.addAddress(address);

                Socket _socket = socket;
                Thread receiver = new Thread(()-> new Receiver(_socket).waitAndThenGetMsg());
                receiver.start();
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail connection.", e);
        }
    }
}