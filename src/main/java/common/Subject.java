package common;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum Subject {
    SERVER("[서버]"), CLIENT("[클라]"), INFO("[INFO]"), WARN("[WARN]");

    private static final String MESSAGE_FORMAT = "{0} {1} {2}";
    private static final DateTimeFormatter MESSAGE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    private final String value;

    Subject(String value) {
        this.value = value;
    }

    public String with(@NonNull LocalDateTime dateTime, @NonNull String message) {
        if(StringUtils.isBlank(message)){
            throw new RuntimeException("message is blank.");
        }
        if(StringUtils.isEmpty(message)){
            throw new RuntimeException("message is empty.");
        }

        return MessageFormat.format(MESSAGE_FORMAT, MESSAGE_TIME_FORMATTER.format(dateTime),value, message);
    }

}
