package common.view.console;

import server.type.Notice;

/**
 * notice 명령어 warn type 을 console 에 출력하기 위한 view 객체.
 */
public class NoticeWarnConsoleMessage extends NoticeConsoleMessage {
    public NoticeWarnConsoleMessage(String message) {
        super(new PrefixNoticeConsoleMessage("\\u001B[31m[WARN]\\u001B[0m"), message);
    }
}
