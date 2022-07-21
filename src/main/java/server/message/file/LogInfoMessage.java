package server.message.file;

import java.time.LocalDateTime;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import static common.Subject.INFO;

public class LogInfoMessage extends LogMessage{

    private LogInfoMessage(@NonNull String value) {
        super(value);
    }

    public static LogInfoMessage of(LocalDateTime dateTime, String message){
        if(StringUtils.isBlank(message)){
            throw new RuntimeException("message is blank.");
        }
        if(StringUtils.isEmpty(message)){
            throw new RuntimeException("message is empty.");
        }

        return new LogInfoMessage(INFO.with(dateTime, message));
    }
}
