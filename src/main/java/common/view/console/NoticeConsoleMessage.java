package common.view.console;

/**
 * client 에서 notice message 를 출력하는 역할.
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
