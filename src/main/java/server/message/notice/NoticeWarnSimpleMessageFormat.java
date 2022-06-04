package server.message.notice;

import lombok.NonNull;

public class NoticeWarnSimpleMessageFormat extends NoticeSimpleMessageFormat {
    public NoticeWarnSimpleMessageFormat(@NonNull String message) {
        super(new PrefixMessage("\\u001B[31m[WARN]\\u001B[0m"), message);
    }
}
