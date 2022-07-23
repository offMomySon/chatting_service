package server.message.file;

import java.time.LocalDateTime;
import static common.Subject.SERVER;

public class LogServerMessageTest extends LogMessageTest{
    @Override
    protected LogMessage createLogMessage(LocalDateTime dateTime, String message) {
        return LogServerMessage.ofCurrent(message);
    }

    @Override
    protected String getPrefix() {
        return SERVER.getPrefix();
    }
}
