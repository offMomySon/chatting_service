package server.v2.writer.file;

import java.util.List;

public class FileWritersV2<T extends FileWriterV2> implements FileWriterV2 {
    private final List<T> fileWriters;

    public FileWritersV2(List<T> fileWriters) {
        this.fileWriters = fileWriters;
    }

    @Override
    public void write(String message) {
        fileWriters.forEach(fileWriter -> fileWriter.write(message));
    }
}
