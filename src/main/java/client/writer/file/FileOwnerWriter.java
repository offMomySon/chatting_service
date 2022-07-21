package client.writer.file;

import client.message.file.FileMessage;
import common.Subject;
import java.text.MessageFormat;
import lombok.NonNull;

public class FileOwnerWriter implements FileWriter {
    private final Subject owner;
    private final BasicFileWriter basicFileWriter;

    public FileOwnerWriter(@NonNull Subject owner, @NonNull BasicFileWriter basicFileWriter) {
        this.owner = owner;
        this.basicFileWriter = basicFileWriter;
    }

    public void write(@NonNull FileMessage fileMessage){
        FileMessage fileMessageWithOwner = new FileMessage(MessageFormat.format("{0} {1}",owner, fileMessage.create()));

        basicFileWriter.write(fileMessageWithOwner);
    }
}
