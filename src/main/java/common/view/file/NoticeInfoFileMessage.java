package common.view.file;

import server.type.Notice;

public class NoticeInfoFileMessage extends NoticeFileMessage {
    public NoticeInfoFileMessage(String message) {
        super(PrefixNoticeFileMessage.create(Notice.INFO), message);
    }
}
