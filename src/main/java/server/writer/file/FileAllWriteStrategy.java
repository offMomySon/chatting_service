package server.writer.file;

import lombok.NonNull;

public class FileAllWriteStrategy implements FileWriteStrategy{
    private final FileWriter fileWriter;
    private final String owner;
    public FileAllWriteStrategy(@NonNull FileWriter fileWriter, String owner) {
        this.fileWriter = fileWriter;
        this.owner = owner;
    }

    @Override
    public void write(String message) {
        fileWriter.writeAll(message, owner);
    }
}
