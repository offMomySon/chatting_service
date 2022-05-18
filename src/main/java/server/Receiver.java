package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import static util.IoUtil.createReader;

/**
 * client 로 부터 메세지를 수신하는 역할.
 */
@Slf4j
public class Receiver {
    private static final int END_CONNECTION = -1;
    private static final String EXIT_CMD = "/exit";
    private static final char[] BUFFER = new char[10];

    private final Socket socket;

    public Receiver(@NonNull Socket socket) {
        this.socket = socket;
    }

    public void waitAndThenGetMsg() {
        BufferedReader in = null;
        try {
            in = createReader(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int readCount = END_CONNECTION;
        try {
            while( (readCount = in.read(BUFFER)) != END_CONNECTION ){
                String msg = String.valueOf(BUFFER, 0, readCount);

                if(Objects.equals(msg, EXIT_CMD)){

//                    NoticeCmd noticeCmd = new NoticeCmd(CmdType.NOTICE, NoticeType.INFO, List.of(IpAddress.create(socket.getInetAddress().getHostAddress())), "서버와의 연결이 종료됬습니다.");
//                    SMFSender smfSender = new SMFSender(ipOutputStreamRepository, ipSupplier);
//                    smfSender.accept(noticeCmd);
//
//                    FileRecorder fileRecorder = new FileRecorder(ipOutputStreamRepository, ipSupplier, "서버");
//                    fileRecorder.accept(noticeCmd);

                    break;
                }

//                FileRecorder fileRecorder = new FileRecorder(ipOutputStreamRepository, ipSupplier, "클라");
//                fileRecorder.accept(msg);

                log.info("From client : {}", msg);
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail to receive msg.",e);
        }
    }
}