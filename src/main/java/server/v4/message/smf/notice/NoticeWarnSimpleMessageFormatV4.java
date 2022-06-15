package server.v4.message.smf.notice;

import common.command.Notice;
import lombok.NonNull;
import server.message.smf.notice.PrefixMessage;

public class NoticeWarnSimpleMessageFormatV4 extends NoticeSimpleMessageFormatV4 {
    public NoticeWarnSimpleMessageFormatV4(@NonNull String message) {
        super(new PrefixMessage(Notice.WARN.getPrefix()), message);
    }
}
