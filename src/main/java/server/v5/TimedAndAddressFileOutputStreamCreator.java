package server.v5;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import server.Address;

public class TimedAndAddressFileOutputStreamCreator {
    private static final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static FileOutputStream create(@NonNull LocalDateTime time, @NonNull Address address) {
        String fileName = MessageFormat.format("{0}_{1}", FILE_NAME_FORMAT.format(time), address.getValue());

        try {
            FileOutputStream out = new FileOutputStream(fileName);
            return out;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("fail to create fileOutputStream");
        }
    }
}
