package server.v5;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NonNull;
import server.Address;

@Getter
public class TimedAddressFileName {
    private static final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final String value;

    private TimedAddressFileName(@NonNull String value) {
        this.value = value;
    }

    public static TimedAddressFileName from(@NonNull LocalDateTime dateTime, @NonNull Address address){
        String name = MessageFormat.format("{0}_{1}",FILE_NAME_FORMAT.format(dateTime), address.getValue());
        return new TimedAddressFileName(name);
    }
}
