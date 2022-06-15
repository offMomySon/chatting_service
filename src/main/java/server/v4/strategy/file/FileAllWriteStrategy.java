package server.v4.strategy.file;

import lombok.NonNull;
import server.v4.MessageWriter;
import server.v4.message.file.FileMessageV4;

public class FileAllWriteStrategy implements FileWriteStrategy{
    private final MessageWriter messageWriter;

    public FileAllWriteStrategy(@NonNull MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    @Override
    public void write(@NonNull FileMessageV4 fileMessage) {
        messageWriter.writeAll(fileMessage);
    }
}
