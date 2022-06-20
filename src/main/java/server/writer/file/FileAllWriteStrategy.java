package server.writer.file;

import lombok.NonNull;
import server.message.file.FileMessage;
import server.v5.MessageWriter;

public class FileAllWriteStrategy implements FileWriteStrategy{
    private final MessageWriter messageWriter;
    public FileAllWriteStrategy(@NonNull MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    @Override
    public void write(FileMessage message) {
        messageWriter.writeAll(message);
    }
}
