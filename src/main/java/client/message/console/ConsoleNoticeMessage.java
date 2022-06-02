package client.message.console;

import common.protocal.notice.PrefixMessage;
import java.text.MessageFormat;
import lombok.NonNull;

public abstract class ConsoleNoticeMessage {
    private final PrefixMessage prefix;
    private final String message;

    protected ConsoleNoticeMessage(@NonNull PrefixMessage prefix, @NonNull String message) {
        this.prefix = prefix;
        this.message = message;
    }

    public String create() {
        return MessageFormat.format("{0} {1}", prefix.create(), message);
    }
}