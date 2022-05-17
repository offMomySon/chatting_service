package common.view.file;

public abstract class NoticeFileMessage implements FileMessage {
    private static final String PREFIX_DELIMITER = " ";

    private final PrefixNoticeFileMessage prefixNoticeFileMessage;
    private final String message;

    protected NoticeFileMessage(PrefixNoticeFileMessage prefixNoticeFileMessage, String message) {
        this.prefixNoticeFileMessage = prefixNoticeFileMessage;
        this.message = message;
    }

    @Override
    public String create() {
        return prefixNoticeFileMessage.create() + message;
    }
}
