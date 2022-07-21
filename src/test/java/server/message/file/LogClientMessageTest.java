package server.message.file;

import common.Subject;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static common.Subject.CLIENT;
import static org.junit.jupiter.api.Assertions.*;

class LogClientMessageTest extends LogMessageTest{

    @Override
    protected LogMessage createLogMessage(LocalDateTime dateTime, String message) {
        return LogClientMessage.of(dateTime, message);
    }

    @Override
    protected String getPrefix() {
        return CLIENT.getValue();
    }

}