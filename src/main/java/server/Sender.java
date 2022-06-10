package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.message.SimpleMessageFormat;
import server.v2.writer.file.BasicFileWriterCreatorV2;
import server.v2.writer.file.FileWritersV2;
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
    private final BasicFileWriterCreatorV2 fileWriterCreator;

    private Sender(@NonNull SmfSender smfSender, @NonNull BufferedReader in, @NonNull BasicFileWriterCreatorV2 fileWriterCreator) {
        this.smfSender = smfSender;
        this.in = in;
        this.fileWriterCreator = fileWriterCreator;
    }

    public static Sender create(@NonNull SmfSender smfSender, @NonNull InputStream in, @NonNull BasicFileWriterCreatorV2 fileWriterCreator) {
        return new Sender(smfSender, createReader(in), fileWriterCreator);
    }

    public void waitAndThenSendMsg() {
        String cmd;
        try {
            while ((cmd = in.readLine()) != STOP_READ) {
                log.info("console write : {}", cmd);

                CmdParser cmdParser = CmdParser.parse(cmd, smfSender, fileWriterCreator);

                SmfSendStrategy smfSendStrategy = cmdParser.getSmfSendStrategy();
                SimpleMessageFormat simpleMessageFormat = cmdParser.getSimpleMessageFormat();
                smfSendStrategy.send(simpleMessageFormat);

                FileWritersV2 fileWritersV2 = cmdParser.getFileWriters();
                String message = cmdParser.getMessage();
                fileWritersV2.write(message);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.", e);
        }
    }
}