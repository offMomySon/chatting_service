package server.protocal.notice;

import lombok.NonNull;

public class NoticeWarnSimpleMessageFormat extends NoticeSimpleMessageFormat{
    public NoticeWarnSimpleMessageFormat(@NonNull PrefixMessage prefix, @NonNull String message) {
        super(new PrefixMessage("\\u001B[31m[WARN]\\u001B[0m"), message);
    }
}
