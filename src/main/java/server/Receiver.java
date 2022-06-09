package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.message.notice.NoticeInfoSimpleMessageFormat;
import server.v2.writer.file.BasicFileWriterV2;
import server.v2.writer.file.FileOwnerWriterV2;
import server.writer.smf.SmfSender;
import util.IoUtil;
import static common.MessageOwner.CLIENT;
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
    private final SmfSender smfSender;

    private Receiver(@NonNull BufferedReader in, @NonNull Address address, SmfSender smfSender) {
        this.in = in;
        this.address = address;
        this.smfSender = smfSender;
    }

    public static Receiver create(@NonNull Socket socket, @NonNull SmfSender smfSender){
        try {
            BufferedReader in = IoUtil.createReader(socket.getInputStream());
            Address address = new Address(socket.getInetAddress().getHostAddress());

            return new Receiver(in, address, smfSender);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void waitAndThenGetMsg() {
        String message = END_CONNECTION;
        try {
            while( (message = in.readLine()) != END_CONNECTION ){
                log.info("From client : {}", message);

                if(Objects.equals(message, EXIT_CMD)){
                    FileOwnerWriterV2 fileWriter = new FileOwnerWriterV2(INFO, BasicFileWriterV2.create(LocalDateTime.now(), address));
                    fileWriter.write(END_MSG);

                    smfSender.send(new NoticeInfoSimpleMessageFormat(END_MSG), List.of(address));
                    break;
                }

                FileOwnerWriterV2 fileWriter = new FileOwnerWriterV2(CLIENT, BasicFileWriterV2.create(LocalDateTime.now(), address));
                fileWriter.write(message);
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail to receive msg.",e);
        }
    }
}