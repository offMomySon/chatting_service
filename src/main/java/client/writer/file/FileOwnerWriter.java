package client.writer.file;

import client.message.file.FileMessage;
import lombok.NonNull;

public class FileOwnerWriter {
    private final String owner;
    private final FileWriter fileWriter;

    public FileOwnerWriter(@NonNull String owner, @NonNull FileWriter fileWriter) {
        this.owner = owner;
        this.fileWriter = fileWriter;
    }

    public void write(FileMessage fileMessage){
        fileWriter.write(fileMessage, owner);
    }
}
