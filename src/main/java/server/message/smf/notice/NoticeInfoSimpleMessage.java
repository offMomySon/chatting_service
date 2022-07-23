package server.message.smf.notice;

import common.command.Notice;
import lombok.NonNull;
import static common.command.Notice.INFO;

public class NoticeInfoSimpleMessage extends NoticeSimpleMessage {
    public NoticeInfoSimpleMessage(@NonNull String message) {
        super(INFO.with(validate(message)));
    }
}
