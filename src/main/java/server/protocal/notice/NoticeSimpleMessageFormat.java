package server.protocal.notice;

import java.text.MessageFormat;
import lombok.NonNull;
import server.protocal.SimpleMessageFormat;

public class NoticeSimpleMessageFormat implements SimpleMessageFormat {
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
