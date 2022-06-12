package server.message.smf.notice;

import common.command.Notice;
import lombok.NonNull;

public class NoticeInfoSimpleMessageFormat extends NoticeSimpleMessageFormat {
    public NoticeInfoSimpleMessageFormat(@NonNull String message) {
        super(new PrefixMessage(Notice.INFO.getPrefix()), message);
    }
}
