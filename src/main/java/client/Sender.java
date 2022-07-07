package client;

import client.message.file.FileMessage;
import client.writer.MessageSender;
import client.writer.file.FileOwnerWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import util.IoUtil;

@Slf4j
class Sender {
    private static final String STOP_READ = null;
    private final BufferedReader reader;
    private final MessageSender messageSender;
    private final FileOwnerWriter fileOwnerWriter;

    private String message = STOP_READ;

    private Sender(@NonNull BufferedReader reader, @NonNull MessageSender messageSender, @NonNull FileOwnerWriter fileOwnerWriter) {
        this.reader = reader;
        this.messageSender = messageSender;
        this.fileOwnerWriter = fileOwnerWriter;
    }

    public static Sender create(@NonNull InputStream inputStream, @NonNull MessageSender messageSender, @NonNull FileOwnerWriter fileOwnerWriter){
        return new Sender(IoUtil.createReader(inputStream), messageSender, fileOwnerWriter);
    }

    public void waitAndThenSendMsg() {
        try {
            while(!Objects.equals(message = reader.readLine(), STOP_READ)){
                log.info("console write : {}", message);

                FileMessage fileMessage = new FileMessage(message);
                fileOwnerWriter.write(fileMessage);

                messageSender.send(message);
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail message send.", e);
        }
    }
}
