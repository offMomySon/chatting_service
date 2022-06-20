package server.writer.file;

import server.message.file.FileMessage;

public interface FileWriteStrategy {
    void write(FileMessage message);
}
