package client;

import client.message.file.FileGenericMessage;
import client.writer.MessageSender;
import client.writer.file.FileOwnerWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import util.IoUtil;

@Slf4j
class Sender {
    private final BufferedReader reader;

    private final MessageSender messageSender;
    private final FileOwnerWriter fileOwnerWriter;

    private Sender(@NonNull BufferedReader reader, @NonNull MessageSender messageSender, @NonNull FileOwnerWriter fileOwnerWriter) {
        this.reader = reader;
        this.messageSender = messageSender;
        this.fileOwnerWriter = fileOwnerWriter;
    }

    public static Sender create(@NonNull InputStream inputStream, @NonNull MessageSender messageSender, @NonNull FileOwnerWriter fileOwnerWriter){
        return new Sender(IoUtil.createReader(inputStream), messageSender, fileOwnerWriter);
    }

    public void waitAndThenSendMsg() {
        String msg;
        try {
            while((msg = reader.readLine()) != null){
                log.info("console write : {}", msg);

                FileGenericMessage fileMessage = new FileGenericMessage(msg);
                fileOwnerWriter.write(fileMessage);

                messageSender.send(msg);
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail message send.", e);
        }
    }
}
