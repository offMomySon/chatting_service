package server.v5;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import server.Address;

public class TimedAddressFileOutputStream extends FileOutputStream {
    private static final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private TimedAddressFileOutputStream(@NotNull String name) throws FileNotFoundException {
        super(name);
    }

    public static TimedAddressFileOutputStream from(@NonNull LocalDateTime dateTime, @NonNull Address address) throws FileNotFoundException {
        String name = MessageFormat.format("{0}_{1}", FILE_NAME_FORMAT.format(dateTime), address.getValue());

        return new TimedAddressFileOutputStream(name);
    }
}
