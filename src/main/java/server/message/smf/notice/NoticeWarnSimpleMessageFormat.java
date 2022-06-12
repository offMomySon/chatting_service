package server.message.smf.notice;

import common.command.Notice;
import lombok.NonNull;

public class NoticeWarnSimpleMessageFormat extends NoticeSimpleMessageFormat {
    public NoticeWarnSimpleMessageFormat(@NonNull String message) {
        super(new PrefixMessage(Notice.WARN.getPrefix()), message);
    }
}
