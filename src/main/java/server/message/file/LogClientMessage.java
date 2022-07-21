package server.message.file;

import java.time.LocalDateTime;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import static common.Subject.CLIENT;

public class LogClientMessage extends LogMessage{
    private LogClientMessage(@NonNull String value) {
        super(value);
    }

    public static LogClientMessage of(LocalDateTime dateTime, String message){
        if(StringUtils.isBlank(message)){
            throw new RuntimeException("message is blank.");
        }
        if(StringUtils.isEmpty(message)){
            throw new RuntimeException("message is empty.");
        }

        return new LogClientMessage(CLIENT.with(dateTime, message));
    }
}
