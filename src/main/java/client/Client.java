package client;


import client.writer.MessageSender;
import client.writer.file.FileOwnerWriter;
import client.writer.file.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
    private final int port;
    private final String serverIp;

    public Client(@NonNull String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = validate(port);
    }

    private static int validate(int port) {
        if (port <= 0) {
            throw new RuntimeException("Abnormal port number.");
        }
        return port;
    }

    public void start() {
        try {
            Socket socket = new Socket(serverIp, port);
            log.info("Connected to server.");

            Thread sender = new Thread(() -> {
                try {
                    InputStream in = System.in;
                    MessageSender messageSender = MessageSender.create(socket.getOutputStream());
                    FileOwnerWriter fileOwnerWriter = new FileOwnerWriter("클라", new FileWriter());

                    Sender.create(in, messageSender, fileOwnerWriter).waitAndThenSendMsg();
                } catch (IOException e) {
                    throw new RuntimeException("Fail get outputStream, from socket.", e);
                }
            });
            Thread receiver = new Thread(() -> {
                try {
                    Receiver.create(socket.getInputStream()).waitAndThenGetMsg();
                } catch (IOException e) {
                    throw new RuntimeException("Fail get inputStream, from socket.", e);
                }
            });

            sender.start();
            receiver.start();
        } catch (IOException e) {
            throw new RuntimeException("Fail connection.", e);
        }
    }
}
