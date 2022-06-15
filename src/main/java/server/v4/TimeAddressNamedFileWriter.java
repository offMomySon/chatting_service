package server.v4;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import server.Address;
import util.IoUtil;

public class TimeAddressNamedFileWriter implements WriterV4 {
    private static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final BufferedWriter out;

    private TimeAddressNamedFileWriter(@NonNull BufferedWriter out) {
        this.out = out;
    }

    public static TimeAddressNamedFileWriter create(@NonNull LocalDateTime time, @NonNull Address address){
        String fileName = MessageFormat.format("{0}_{1}", FILE_NAME_FORMATTER.format(time), address.getValue());

        BufferedWriter writer = IoUtil.createFileAppender(fileName);

        System.out.println("create TimeAddressNamedFileWriter writer");
        return new TimeAddressNamedFileWriter(writer);
    }

    @Override
    public void write(@NonNull String message) {
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("message 출력에 실패했습니다.");
        }
    }
}
