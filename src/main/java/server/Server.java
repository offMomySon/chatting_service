package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.v4.MessageWriter;
import server.v4.SimpleFormatMessageWriter;
import server.v4.TimeAddressNamedFileWriter;

@Slf4j
public class Server {
    private static final int MIN_PORT_NUM = 7777;

    private final int port;

    private final MessageWriter messageWriter;

    public Server(@NonNull MessageWriter messageWriter, int port) {
        this.port = validate(port);
        this.messageWriter = messageWriter;
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
            () -> Sender.create(System.in, messageWriter).waitAndThenSendMsg()
        );
        sender.start();

        try( ServerSocket serverSocket = new ServerSocket(port) ) {
            log.info("Server start.");

            while(true) {
                socket = serverSocket.accept();

                Address address = new Address(socket.getInetAddress().getHostAddress());

                SimpleFormatMessageWriter simpleFormatMessageWriter = SimpleFormatMessageWriter.create(socket.getOutputStream());
                TimeAddressNamedFileWriter timeAddressNamedFileWriter = TimeAddressNamedFileWriter.create(LocalDateTime.now(),address);

                messageWriter.addAddress(address, simpleFormatMessageWriter);
                messageWriter.addAddress(address, timeAddressNamedFileWriter);

                Socket _socket = socket;
                Thread receiver = new Thread(()-> Receiver.create(_socket, messageWriter).waitAndThenGetMsg());
                receiver.start();
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail connection.", e);
        }
    }
}