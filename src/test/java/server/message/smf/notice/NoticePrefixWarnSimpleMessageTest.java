package server.message.smf.notice;

import server.message.smf.SimpleMessage;
import static common.command.NoticePrefix.WARN;

class NoticePrefixWarnSimpleMessageTest extends NoticePrefixSimpleMessageTest {

    @Override
    protected SimpleMessage createLogMessage(String message) {
        return new NoticeWarnSimpleMessage(message);
    }

    @Override
    protected String getPrefix() {
        return WARN.getValue();
    }
}