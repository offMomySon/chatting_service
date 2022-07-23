package server.message.smf.notice;

import common.command.Notice;
import server.message.smf.SimpleMessage;
import static common.command.Notice.INFO;

class NoticeInfoSimpleMessageTest extends NoticeSimpleMessageTest{

    @Override
    protected SimpleMessage createLogMessage(String message) {
        return new NoticeInfoSimpleMessage(message);
    }

    @Override
    protected String getPrefix() {
        return INFO.getPrefix();
    }
}