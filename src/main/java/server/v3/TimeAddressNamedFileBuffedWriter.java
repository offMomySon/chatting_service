package server.v3;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NonNull;
import server.Address;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * file name 을 관리하는 데이터성 도메인
 */
@Getter
public class TimeAddressNamedFileBuffedWriter {
    private static final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    private final BufferedWriter value;

    private TimeAddressNamedFileBuffedWriter(@NonNull BufferedWriter value) {
        this.value = value;
    }

    public static TimeAddressNamedFileBuffedWriter from(@NonNull LocalDateTime time, @NonNull Address address){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(MessageFormat.format("{0}_{1}", FILE_NAME_FORMAT.format(time), address.getValue()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("fileOutputStream 생성에 실패 했습니다.");
        }

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 8192);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, UTF_8);
        BufferedWriter out = new BufferedWriter(outputStreamWriter, 8192);

        return new TimeAddressNamedFileBuffedWriter(out);
    }
}
