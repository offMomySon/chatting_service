package server.message.file;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static common.Subject.INFO;

public class LogInfoMessageTest extends LogMessageTest{
    @Override
    protected LogMessage createLogMessage(LocalDateTime dateTime, String message) {
        return LogInfoMessage.ofCurrent(message);
    }

    @Override
    protected String getPrefix() {
        return INFO.getPrefix();
    }
}
