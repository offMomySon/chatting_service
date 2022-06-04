package server;

import command.Cmd;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.writer.file.FileWriter;
import server.writer.smf.SmfSender;
import static util.IoUtil.createReader;

/**
 * server 에서 입력 받은 cmd 를 client 에 뿌리는 역할.
 */
@Slf4j
class Sender {
    private static final String STOP_READ = null;
    private final BufferedReader in;
    private final SmfSender smfSender;
    private final FileWriter fileWriter;

    private Sender(@NonNull SmfSender smfSender, @NonNull FileWriter fileWriter, @NonNull BufferedReader in) {
        this.smfSender = smfSender;
        this.fileWriter = fileWriter;
        this.in = in;
    }

    public static Sender create(@NonNull SmfSender smfSender, @NonNull FileWriter fileWriter, @NonNull InputStream in) {
        return new Sender(smfSender, fileWriter, createReader(in));
    }

    public void waitAndThenSendMsg() {
        String cmd;
        try {
            while ((cmd = in.readLine()) != STOP_READ) {
                log.info("console write : {}", cmd);

            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.", e);
        }
    }

    private String getDestination(String scmd) {
        String[] splitCmd = scmd.split(" ");

        Cmd cmd = Cmd.from(splitCmd[0]).orElseThrow(() -> new RuntimeException("일치하는 cmd 가 존재하지 않습니다."));

        switch (cmd) {
            case SEND:
                return splitCmd[1];
            case NOTICE:
                return splitCmd[2];
            default:
                throw new RuntimeException("일치하는 cmd 가 존재하지 않습니다.");
        }
    }
}