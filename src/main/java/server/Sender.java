package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.message.SimpleMessageFormat;
import server.writer.file.FileWriteStrategy;
import server.writer.file.FileWriter;
import server.writer.smf.SmfSendStrategy;
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

                CmdParser cmdParser = CmdParser.parse(cmd, smfSender, fileWriter);

                SmfSendStrategy smfSendStrategy = cmdParser.getSmfSendStrategy();
                SimpleMessageFormat simpleMessageFormat = cmdParser.getSimpleMessageFormat();
                smfSendStrategy.send(simpleMessageFormat);

                FileWriteStrategy fileWriteStrategy = cmdParser.getFileWriteStrategy();
                String message = cmdParser.getMessage();
                fileWriteStrategy.write(message);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.", e);
        }
    }
}