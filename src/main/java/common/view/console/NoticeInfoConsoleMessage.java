package common.view.console;

import server.type.Notice;

/**
 * notice 명령어 info 타입을 console 에 출력하기 위한 view 객체.
 */
public class NoticeInfoConsoleMessage extends NoticeConsoleMessage {
    public NoticeInfoConsoleMessage(String message) {
        super(new PrefixNoticeConsoleMessage("\\u001B[33m[INFO]\\u001B[0m"), message);
    }
}
