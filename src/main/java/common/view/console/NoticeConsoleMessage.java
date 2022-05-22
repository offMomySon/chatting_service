package common.view.console;

/**
 * notice 명령어를 console 에 출력하기 위한 메세지 view.
 */
public abstract class NoticeConsoleMessage implements ConsoleMessage {
    private static final String PREFIX_DELIMITER = " ";

    private final PrefixNoticeConsoleMessage prefixNoticeConsoleMessage;
    private final String message;

    public NoticeConsoleMessage(PrefixNoticeConsoleMessage prefixNoticeConsoleMessage, String message) {
        this.prefixNoticeConsoleMessage = prefixNoticeConsoleMessage;
        this.message = message;
    }

    @Override
    public String create() {
        return prefixNoticeConsoleMessage.create() + PREFIX_DELIMITER + message;
    }
}
