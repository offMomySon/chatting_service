package server.v4;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import server.Address;
import static java.nio.charset.StandardCharsets.UTF_8;

public class TimeAddressNamedFileWriter implements WriterV4{
    private static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final BufferedWriter out;

    private TimeAddressNamedFileWriter(@NonNull BufferedWriter out) {
        this.out = out;
    }

    public static TimeAddressNamedFileWriter create(@NonNull LocalDateTime time, @NonNull Address address){
        String fileName = MessageFormat.format("{0}_{1}", FILE_NAME_FORMATTER.format(time), address.getValue());

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("file stream 생성에 실패했습니다.");
        }
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 8192);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, UTF_8);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter, 8192);

        return new TimeAddressNamedFileWriter(bufferedWriter);
    }

    @Override
    public void write(@NonNull String message) {
        try(out) {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("message 출력에 실패했습니다.");
        }
    }
}
