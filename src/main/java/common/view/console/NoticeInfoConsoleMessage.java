package common.view.console;

import server.type.Notice;

public class NoticeInfoConsoleMessage extends NoticeConsoleMessage {
    public NoticeInfoConsoleMessage(String message) {
        super(PrefixNoticeConsoleMessage.create(Notice.INFO), message);
    }
}
