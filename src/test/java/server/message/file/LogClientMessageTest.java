package server.message.file;

import java.time.LocalDateTime;
import static common.Subject.CLIENT;

class LogClientMessageTest extends LogMessageTest{

    @Override
    protected LogMessage createLogMessage(LocalDateTime dateTime, String message) {
        return LogClientMessage.ofCurrent(message);
    }

    @Override
    protected String getPrefix() {
        return CLIENT.getPrefix();
    }

}