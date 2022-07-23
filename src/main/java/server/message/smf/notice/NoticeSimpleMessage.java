package server.message.smf.notice;

import java.text.MessageFormat;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import server.message.smf.SimpleMessage;

public abstract class NoticeSimpleMessage extends SimpleMessage {
    private static final int code = 1;

    protected NoticeSimpleMessage(@NonNull String value) {
        super(MessageFormat.format("{0}:{1}",code, validate(value)));
    }
}
