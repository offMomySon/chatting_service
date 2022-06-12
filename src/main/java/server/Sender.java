package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.message.file.FileMessage;
import server.message.smf.SimpleMessageFormat;
import server.v3.AddressRepository;
import server.v3.CmdParserV3;
import server.v3.FileWritersV3;
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
    private final AddressRepository addressRepository;

    private Sender(@NonNull SmfSender smfSender, @NonNull BufferedReader in, @NonNull AddressRepository addressRepository) {
        this.smfSender = smfSender;
        this.in = in;
        this.addressRepository = addressRepository;
    }

    public static Sender create(@NonNull SmfSender smfSender, @NonNull InputStream in, AddressRepository fileWriterCreator) {
        return new Sender(smfSender, createReader(in), fileWriterCreator);
    }

    public void waitAndThenSendMsg() {
        String cmd;
        try {
            while ((cmd = in.readLine()) != STOP_READ) {
                log.info("console write : {}", cmd);

//                CmdParser cmdParser = CmdParser.parse(cmd, smfSender, addressRepository);
                CmdParserV3 cmdParserV3 = CmdParserV3.parse(cmd, smfSender, addressRepository);

                SmfSendStrategy smfSendStrategy = cmdParserV3.getSmfSendStrategy();
                SimpleMessageFormat simpleMessageFormat = cmdParserV3.getSimpleMessageFormat();
                smfSendStrategy.send(simpleMessageFormat);

                FileWritersV3 fileWritersV3 = cmdParserV3.getFileWritersV3();
                FileMessage fileMessage = cmdParserV3.getFileMessage();
                fileWritersV3.write(fileMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.", e);
        }
    }
}