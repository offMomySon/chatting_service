package server.message.file;

import common.Subject;
import java.time.LocalDateTime;
import static common.Subject.SERVER;

public class LogServerMessageTest extends LogMessageTest{
    @Override
    protected LogMessage createLogMessage(LocalDateTime dateTime, String message) {
        return LogServerMessage.of(dateTime, message);
    }

    @Override
    protected String getPrefix() {
        return SERVER.getValue();
    }
}
