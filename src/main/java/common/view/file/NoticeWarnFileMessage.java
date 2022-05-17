package common.view.file;

import server.type.Notice;

public class NoticeWarnFileMessage extends NoticeFileMessage {
    public NoticeWarnFileMessage(String message) {
        super(PrefixNoticeFileMessage.create(Notice.WARN), message);
    }
}
