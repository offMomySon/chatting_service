package client.writer.file;

import client.message.file.FileMessage;
import client.writer.Writer;
import lombok.NonNull;

public class FileWriteHmm implements Writer {
    private final FileWriter writer;
    private final FileMessage message;

    public FileWriteHmm(@NonNull FileWriter writer, @NonNull FileMessage message) {
        this.writer = writer;
        this.message = message;
    }

    @Override
    public void write() {
        writer.write(message);
    }
}
