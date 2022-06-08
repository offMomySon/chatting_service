package client.writer.file;

import client.message.file.FileMessage;
import java.text.MessageFormat;
import lombok.NonNull;

public class FileOwnerWriter implements FileWriterInterface{
    private final String owner;
    private final FileWriter fileWriter;

    public FileOwnerWriter(@NonNull String owner, @NonNull FileWriter fileWriter) {
        this.owner = owner;
        this.fileWriter = fileWriter;
    }

    public void write(@NonNull FileMessage fileMessage){
        FileMessage fileMessageWithOwner = new FileMessage(MessageFormat.format("{0} {1}",owner, fileMessage.create()));

        fileWriter.write(fileMessageWithOwner);
    }
}
