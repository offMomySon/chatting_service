package server.v4.strategy.file;

import server.v4.message.file.FileMessageV4;

public interface FileWriteStrategy {
    void write(FileMessageV4 fileMessage);
}
