package common.view.console;

import server.type.Notice;

/**
 * TODO
 * view 의 사소한 특징까지 class 를 나누는게 좋을까?..
 *
 */
public class NoticeWarnConsoleMessage extends NoticeConsoleMessage {
    public NoticeWarnConsoleMessage(String message) {
        super(PrefixNoticeConsoleMessage.create(Notice.WARN), message);
    }
}
