package common.view.file;

/**
 * notice 명령어를 file 에 출력하기 위한 view 객체.
 */
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
