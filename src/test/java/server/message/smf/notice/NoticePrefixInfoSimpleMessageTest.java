package server.message.smf.notice;

import server.message.smf.SimpleMessage;
import static common.command.NoticePrefix.INFO;

class NoticePrefixInfoSimpleMessageTest extends NoticePrefixSimpleMessageTest {

    @Override
    protected SimpleMessage createLogMessage(String message) {
        return new NoticeInfoSimpleMessage(message);
    }

    @Override
    protected String getPrefix() {
        return INFO.getValue();
    }
}