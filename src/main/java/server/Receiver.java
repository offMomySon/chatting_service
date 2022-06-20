package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.message.file.FileMessage;
import server.message.smf.notice.NoticeInfoSimpleMessageFormat;
import server.v5.Destination;
import server.v5.MessageWriter;
import server.v5.Usage;
import util.IoUtil;
import static common.MessageOwner.INFO;

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
    private final MessageWriter messageWriter;

    private Receiver(@NonNull BufferedReader in, @NonNull Address address, @NonNull MessageWriter messageWriter) {
        this.in = in;
        this.address = address;
        this.messageWriter = messageWriter;
    }

    public static Receiver create(@NonNull Socket socket, @NonNull MessageWriter messageWriter) {
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
            while (!Objects.equals(message = in.readLine(), END_CONNECTION)) {
                log.info("From client : {}", message);

                if (Objects.equals(message, EXIT_CMD)) {
                    FileMessage fileMessage = new FileMessage(LocalDateTime.now(), INFO, message);
                    messageWriter.write(new Destination(address, Usage.FILE), fileMessage);

                    NoticeInfoSimpleMessageFormat smfMessage = new NoticeInfoSimpleMessageFormat(message);
                    messageWriter.write(new Destination(address, Usage.SOCKET), smfMessage);
                    break;
                }

                FileMessage fileMessage = new FileMessage(LocalDateTime.now(), INFO, message);
                messageWriter.write(new Destination(address, Usage.FILE), fileMessage);
            }
        } catch (IOException e){
            throw new RuntimeException("Fail to receive msg.", e);
        }
    }
}