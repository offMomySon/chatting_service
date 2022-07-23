package server.message.smf.notice;

import common.command.Notice;
import lombok.NonNull;
import static common.command.Notice.*;

public class NoticeWarnSimpleMessage extends NoticeSimpleMessage {
    public NoticeWarnSimpleMessage(@NonNull String message) {
        super(WARN.with(validate(message)));
    }
}
