package server.message.file;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import static common.Subject.SERVER;
import static common.Subject.WARN;

public class LogWarnMessage extends LogMessage{
    private static final DateTimeFormatter MESSAGE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    private LogWarnMessage(@NonNull String value) {
        super(validate(value));
    }

    private static LogWarnMessage of(LocalDateTime dateTime, String message){
        String formedMessage = MessageFormat.format("{0} {1}", MESSAGE_TIME_FORMATTER.format(dateTime), WARN.with(validate(message)));
        return new LogWarnMessage(formedMessage);
    }

    public static LogWarnMessage ofCurrent(String message){
        return of(LocalDateTime.now(), message);
    }
}
