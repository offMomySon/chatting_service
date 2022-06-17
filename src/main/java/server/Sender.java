package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.v5.AddressWriter;
import static util.IoUtil.createReader;

/**
 * server 에서 입력 받은 cmd 를 client 에 뿌리는 역할.
 */
@Slf4j
class Sender {
    private static final String STOP_READ = null;
    private final BufferedReader in;

    private final AddressWriter addressWriter;

    private Sender(@NonNull BufferedReader in, @NonNull AddressWriter addressWriter) {
        this.in = in;
        this.addressWriter = addressWriter;
    }

    public static Sender create(@NonNull InputStream in, AddressWriter messageWriter) {
        return new Sender(createReader(in), messageWriter);
    }

    public void waitAndThenSendMsg() {
        String cmd;
        try {
            while ((cmd = in.readLine()) != STOP_READ) {
                log.info("console write : {}", cmd);

//                CmdParserV4 cmdParserV4 = CmdParserV4.parse(cmd, messageWriter);
//
//                SmfWriteStrategy smfSendStrategy = cmdParserV4.getSmfSendStrategy();
//                SimpleMessageFormatV4 simpleMessageFormatV4 = cmdParserV4.getSimpleMessageFormatV4();
//                smfSendStrategy.write(simpleMessageFormatV4);
//
//                FileWriteStrategy fileWriteStrategy = cmdParserV4.getFileWriteStrategy();
//                FileMessageV4 fileMessage = cmdParserV4.getFileMessage();
//                fileWriteStrategy.write(fileMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.", e);
        }
    }
}