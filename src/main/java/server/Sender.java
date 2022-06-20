package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.message.file.FileMessage;
import server.message.smf.SimpleMessageFormat;
import server.v5.MessageWriter;
import server.writer.file.FileWriteStrategy;
import server.writer.smf.SmfSendStrategy;
import static util.IoUtil.createReader;

/**
 * server 에서 입력 받은 cmd 를 client 에 뿌리는 역할.
 */
@Slf4j
class Sender {
    private static final String STOP_READ = null;
    private final BufferedReader in;

    private final MessageWriter messageWriter;

    private Sender(@NonNull BufferedReader in, @NonNull MessageWriter messageWriter) {
        this.in = in;
        this.messageWriter = messageWriter;
    }

    public static Sender create(@NonNull InputStream in, MessageWriter messageWriter) {
        return new Sender(createReader(in), messageWriter);
    }

    public void waitAndThenSendMsg() {
        String cmd;
        try {
            while ((cmd = in.readLine()) != STOP_READ) {
                log.info("console write : {}", cmd);

                NewCmdParser cmdParser = NewCmdParser.parse(cmd, messageWriter);
                FileWriteStrategy fileWriteStrategy = cmdParser.getFileWriteStrategy();
                FileMessage fileMessage = cmdParser.getFileMessage();
                fileWriteStrategy.write(fileMessage);

                SmfSendStrategy smfSendStrategy = cmdParser.getSmfSendStrategy();
                SimpleMessageFormat smfMessage = cmdParser.getSmfMessage();
                smfSendStrategy.send(smfMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.", e);
        }
    }
}