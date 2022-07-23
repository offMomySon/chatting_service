package server.message.file;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import static common.Subject.CLIENT;
import static common.Subject.INFO;
import static common.Subject.WARN;

public class LogInfoMessage extends LogMessage{
    private static final DateTimeFormatter MESSAGE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    private LogInfoMessage(String value) {
        super(validate(value));
    }

    private static LogInfoMessage of(LocalDateTime dateTime, String message){
        String formedMessage = MessageFormat.format("{0} {1}", MESSAGE_TIME_FORMATTER.format(dateTime), INFO.with(validate(message)));
        return new LogInfoMessage(formedMessage);
    }

    public static LogInfoMessage ofCurrent(String message){
        return of(LocalDateTime.now(), message);
    }
}
