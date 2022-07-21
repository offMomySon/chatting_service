package server.message.file;

import common.Subject;
import java.time.LocalDateTime;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public class LogWarnMessage extends LogMessage{
    private LogWarnMessage(@NonNull String value) {
        super(value);
    }

    public static LogWarnMessage of(@NonNull LocalDateTime dateTime, @NonNull String message){
        if(StringUtils.isBlank(message)){
            throw new RuntimeException("message is blank.");
        }
        if(StringUtils.isEmpty(message)){
            throw new RuntimeException("message is empty.");
        }

        return new LogWarnMessage(Subject.WARN.with(dateTime, message));
    }
}
