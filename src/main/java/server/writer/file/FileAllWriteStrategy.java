package server.writer.file;

import lombok.NonNull;
import server.message.file.FileMessage;
import server.v5.MessageWriter;
import static server.v5.Usage.FILE;

public class FileAllWriteStrategy implements FileWriteStrategy{
    private final MessageWriter messageWriter;
    private final FileMessage message;
    public FileAllWriteStrategy(@NonNull MessageWriter messageWriter, @NonNull FileMessage message) {
        this.messageWriter = messageWriter;
        this.message = message;
    }

    @Override
    public void write() {
        messageWriter.writeAll(FILE, message);
    }
}
