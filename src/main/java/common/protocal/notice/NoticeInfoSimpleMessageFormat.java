package common.protocal.notice;

import lombok.NonNull;

public class NoticeInfoSimpleMessageFormat extends NoticeSimpleMessageFormat {
    public NoticeInfoSimpleMessageFormat(@NonNull String message) {
        super(new PrefixMessage("\\u001B[33m[INFO]\\u001B[0m"), message);
    }
}
