package server.message.file;

import common.Subject;
import java.time.LocalDateTime;
import static common.Subject.INFO;

public class LogInfoMessageTest extends LogMessageTest{
    @Override
    protected LogMessage createLogMessage(LocalDateTime dateTime, String message) {
        return LogInfoMessage.of(dateTime, message);
    }

    @Override
    protected String getPrefix() {
        return INFO.getValue();
    }
}
