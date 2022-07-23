package server.message.smf.notice;

import common.command.Notice;
import server.message.smf.SimpleMessage;
import static common.command.Notice.WARN;
import static org.junit.jupiter.api.Assertions.*;

class NoticeWarnSimpleMessageTest extends NoticeSimpleMessageTest{

    @Override
    protected SimpleMessage createLogMessage(String message) {
        return new NoticeWarnSimpleMessage(message);
    }

    @Override
    protected String getPrefix() {
        return WARN.getPrefix();
    }
}