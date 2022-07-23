package server.message.file;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import static common.Subject.CLIENT;
import static common.Subject.INFO;

public class LogClientMessage extends LogMessage{
    private static final DateTimeFormatter MESSAGE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    private LogClientMessage(String value) {
        super(validate(value));
    }

    private static LogClientMessage of( LocalDateTime dateTime, String message){
        String formedMessage = MessageFormat.format("{0} {1}", MESSAGE_TIME_FORMATTER.format(dateTime), CLIENT.with(validate(message)));
        return new LogClientMessage(formedMessage);
    }

    public static LogClientMessage ofCurrent(String message){
        return of(LocalDateTime.now(), message);
    }
}
