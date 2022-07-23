package server.message.smf.notice;

import java.text.MessageFormat;
import lombok.NonNull;
import server.message.smf.SimpleMessage;

public abstract class NoticeSimpleMessage extends SimpleMessage {
    protected NoticeSimpleMessage(@NonNull String value) {
        super(MessageFormat.format("{0}:{1}", 1, validate(value)));
    }
}
