package server.message.file;

import java.time.LocalDateTime;
import static common.Subject.WARN;

public class LogWarnMessageTest extends LogMessageTest{
    @Override
    protected LogMessage createLogMessage(LocalDateTime dateTime, String message) {
        return LogWarnMessage.ofCurrent(message);
    }

    @Override
    protected String getPrefix() {
        return WARN.getPrefix();
    }
}
