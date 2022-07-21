package server.message.file;

import common.Subject;
import java.time.LocalDateTime;
import static common.Subject.WARN;

public class LogWarnMessageTest extends LogMessageTest{
    @Override
    protected LogMessage createLogMessage(LocalDateTime dateTime, String message) {
        return LogWarnMessage.of(dateTime,message);
    }

    @Override
    protected String getPrefix() {
        return WARN.getValue();
    }
}
