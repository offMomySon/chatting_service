package server.v4.message.smf.notice;

import java.text.MessageFormat;
import lombok.NonNull;
import server.message.smf.notice.PrefixMessage;
import server.v4.message.smf.SimpleMessageFormatV4;

public class NoticeSimpleMessageFormatV4 implements SimpleMessageFormatV4 {
    private final server.message.smf.notice.PrefixMessage prefix;
    private final String message;

    protected NoticeSimpleMessageFormatV4(@NonNull PrefixMessage prefix, @NonNull String message) {
        this.prefix = prefix;
        this.message = message;
    }

    @Override
    public String createMessage() {
        return MessageFormat.format("1:{0} {1}", prefix.create(), message);
    }
}
