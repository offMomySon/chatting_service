package common.view.file;

import server.type.Notice;

/**
 * notice 명령어의 info 타입을, file 에 출력하기 위한 view 객체.
 */
public class NoticeInfoFileMessage extends NoticeFileMessage {
    public NoticeInfoFileMessage(String message) {
        super(new PrefixNoticeFileMessage("[INFO]"), message);
    }
}
