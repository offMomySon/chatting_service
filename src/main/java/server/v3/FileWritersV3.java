package server.v3;


import common.Writer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.message.file.FileMessage;

/**
 * time, adderess 이름을 가진 outputStream 에 file message 를 출력하는 역할.
 *
 * 도메인적으로 어떤 가치가 있지..?
 */
public class FileWritersV3 {
    private final List<Writer> writers;

    private FileWritersV3(@NonNull List<Writer> writers) {
        this.writers = writers;
    }

    public static FileWritersV3 from(@NonNull List<TimeAddressNamedFileBuffedWriter> fileWriters){
        List<Writer> writers = fileWriters.stream()
            .map(f -> new Writer(f.getValue()))
            .collect(Collectors.toUnmodifiableList());

        return new FileWritersV3(writers);
    }

    public void write(FileMessage message){
        writers.forEach(writer -> writer.write(message.getMessage()));
    }
}
