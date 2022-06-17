package server.message.smf.notice;

import java.text.MessageFormat;
import lombok.NonNull;
import server.message.smf.SimpleMessageFormat;

public class NoticeSimpleMessageFormat implements SimpleMessageFormat {
    private final PrefixMessage prefix;
    private final String message;

    protected NoticeSimpleMessageFormat(@NonNull PrefixMessage prefix, @NonNull String message) {
        this.prefix = prefix;
        this.message = message;
    }

    @Override
    public String create() {
        return MessageFormat.format("1:{0} {1}", prefix.create(), message);
    }
}
