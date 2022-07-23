package server.message.smf.notice;

import lombok.NonNull;
import static common.command.NoticePrefix.INFO;

public class NoticeInfoSimpleMessage extends NoticeSimpleMessage {
    public NoticeInfoSimpleMessage(@NonNull String message) {
        super(INFO.with(validate(message)));
    }
}
