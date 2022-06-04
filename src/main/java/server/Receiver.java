package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.message.notice.NoticeInfoSimpleMessageFormat;
import server.writer.file.FileWriter;
import server.writer.smf.SmfSender;
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
    private final SmfSender smfSender;
    private final FileWriter fileWriter;

    private Receiver(@NonNull BufferedReader in, @NonNull Address address, SmfSender smfSender, @NonNull FileWriter fileWriter) {
        this.in = in;
        this.address = address;
        this.smfSender = smfSender;
        this.fileWriter = fileWriter;
    }

    public static Receiver create(@NonNull Socket socket, @NonNull SmfSender smfSender, @NonNull FileWriter fileWriter){
        try {
            BufferedReader in = IoUtil.createReader(socket.getInputStream());
            Address address = new Address(socket.getInetAddress().getHostAddress());

            return new Receiver(in, address, smfSender, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void waitAndThenGetMsg() {
        String msg = END_CONNECTION;
        try {
            while( (msg = in.readLine()) != END_CONNECTION ){
                log.info("From client : {}", msg);

                if(Objects.equals(msg, EXIT_CMD)){
                    fileWriter.write(END_MSG, "INFO", List.of(address));
                    smfSender.send(new NoticeInfoSimpleMessageFormat(END_MSG), List.of(address));
                    break;
                }

                fileWriter.write(msg, "클라", List.of(address));
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail to receive msg.",e);
        }
    }
}