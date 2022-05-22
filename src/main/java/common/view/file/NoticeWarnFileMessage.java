package common.view.file;

import server.type.Notice;

/**
 * notice 명령어 warn type 을 file 에 출력하기 위한 view 객체.
 */
public class NoticeWarnFileMessage extends NoticeFileMessage {
    public NoticeWarnFileMessage(String message) {
        super(new PrefixNoticeFileMessage("[WARN]"), message);
    }
}
