package server.message.smf.generic;

import server.message.file.LogMessage;
import server.message.smf.SimpleMessage;
import server.message.smf.SimpleMessageTest;
import static org.junit.jupiter.api.Assertions.*;

class GenericSimpleMessageTest extends SimpleMessageTest {

    @Override
    protected SimpleMessage createLogMessage(String message) {
        return new GenericSimpleMessage(message);
    }

    @Override
    protected String getCode() {
        return "0";
    }
}