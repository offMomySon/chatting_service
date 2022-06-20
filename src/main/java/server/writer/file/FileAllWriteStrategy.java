package server.writer.file;

import lombok.NonNull;
import server.message.file.FileMessage;
import server.v5.MessageWriter;
import static server.v5.Usage.FILE;

public class FileAllWriteStrategy implements FileWriteStrategy{
    private final MessageWriter messageWriter;
    public FileAllWriteStrategy(@NonNull MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    @Override
    public void write(@NonNull FileMessage message) {
        messageWriter.writeAll(FILE, message);
    }
}
