package server.v4.message.smf.notice;

import common.command.Notice;
import lombok.NonNull;
import server.message.smf.notice.PrefixMessage;

public class NoticeInfoSimpleMessageFormatV4 extends NoticeSimpleMessageFormatV4 {
    public NoticeInfoSimpleMessageFormatV4(@NonNull String message) {
        super(new PrefixMessage(Notice.INFO.getPrefix()), message);
    }
}
