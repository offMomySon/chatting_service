package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import repository.IpOutputStreamRepository;
import server.domain.IpAddress;
import server.domain.IpAddresses;
import static util.IoUtil.createReader;

/**
 * client 로 부터 메세지를 수신하는 역할.
 */
@Slf4j
public class Receiver {
    private static final int END_CONNECTION = -1;
    public static final char[] BUFFER = new char[20];

    private final IpOutputStreamRepository ipRepository;
    private final InetAddress inetAddress;
    private final int port;
    private final BufferedReader in;

    private Receiver(@NonNull BufferedReader in, @NonNull InetAddress inetAddress, int port, @NonNull IpOutputStreamRepository ipRepository) {
        this.inetAddress = inetAddress;
        this.port = validate(port);
        this.in = in;
        this.ipRepository = ipRepository;
    }

    private static int validate(int port){
        if(port <= 0){
            throw new RuntimeException("Abnormal port number.");
        }
        return port;
    }

    public static Receiver create(@NonNull Socket socket, @NonNull IpOutputStreamRepository ipRepository){
        BufferedReader reader = null;
        try {
            reader = createReader(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Fail get inputStream, from socket.", e);
        }

        return new Receiver(reader, socket.getInetAddress(), socket.getPort(), ipRepository);
    }

    public void waitAndThenGetMsg() {
        try {
            int readCount = END_CONNECTION;
            while( (readCount = in.read(BUFFER)) != END_CONNECTION ){
                String msg = String.valueOf(BUFFER, 0, readCount);

                log.info("From client : {}", msg);
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail to receive msg.",e);
        } finally {
            log.info("[System Alert] [() : ()] is out.", inetAddress, port);
            ipRepository.remove(new IpAddress(inetAddress.getHostAddress()));

            log.info("Current user count : {}", ipRepository.getSize());
        }
    }
}