package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.v5.AddressWriter;
import util.IoUtil;

/**
 * client 로 부터 메세지를 수신하는 역할.
 */
@Slf4j
public class Receiver {
    private static final String END_CONNECTION = null;
    private static final String EXIT_CMD = "/exit";
    private static final String END_MSG = "서버와의 연결이 종료됬습니다.";

    private final BufferedReader in;
    private final Address address;
    private final AddressWriter addressWriter;

    private Receiver(@NonNull BufferedReader in, @NonNull Address address, @NonNull AddressWriter addressWriter) {
        this.in = in;
        this.address = address;
        this.addressWriter = addressWriter;
    }

    public static Receiver create(@NonNull Socket socket, @NonNull AddressWriter messageWriter) {
        try {
            BufferedReader in = IoUtil.createReader(socket.getInputStream());
            Address address = new Address(socket.getInetAddress().getHostAddress());

            return new Receiver(in, address, messageWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitAndThenGetMsg() {
        String message = END_CONNECTION;
        try {
            while ((message = in.readLine()) != END_CONNECTION) {
                log.info("From client : {}", message);
//
//                if (Objects.equals(message, EXIT_CMD)) {
//                    FileMessageV4 fileMessage = new FileMessageV4(LocalDateTime.now(), INFO, message);
//                    messageWriter.write(fileMessage, List.of());
//
//                    NoticeInfoSimpleMessageFormat smfMessage = new NoticeInfoSimpleMessageFormat(END_MSG);
//                    messageWriter.write(smfMessage, List.of(address));
//                    break;
//                }
//
//                FileMessageV4 fileMessage = new FileMessageV4(LocalDateTime.now(), CLIENT, message);
//                messageWriter.write(fileMessage, List.of());
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail to receive msg.", e);
        }
    }
}