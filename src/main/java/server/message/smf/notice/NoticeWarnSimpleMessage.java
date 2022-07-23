package server.message.smf.notice;

import lombok.NonNull;
import static common.command.NoticePrefix.*;

public class NoticeWarnSimpleMessage extends NoticeSimpleMessage {
    public NoticeWarnSimpleMessage(@NonNull String message) {
        super(WARN.with(validate(message)));
    }
}
