package server.message.smf.notice;

import java.text.MessageFormat;
import lombok.NonNull;
import server.v4.message.smf.SimpleMessageFormatV4;

public class NoticeSimpleMessageFormat implements SimpleMessageFormatV4 {
    private final PrefixMessage prefix;
    private final String message;

    protected NoticeSimpleMessageFormat(@NonNull PrefixMessage prefix, @NonNull String message) {
        this.prefix = prefix;
        this.message = message;
    }

    @Override
    public String createMessage() {
        return MessageFormat.format("1:{0} {1}", prefix.create(), message);
    }
}
