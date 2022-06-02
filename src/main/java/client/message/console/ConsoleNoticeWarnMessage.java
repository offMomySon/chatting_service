package client.message.console;

import common.protocal.notice.PrefixMessage;
import lombok.NonNull;

public class ConsoleNoticeWarnMessage extends ConsoleNoticeMessage {
    public ConsoleNoticeWarnMessage(@NonNull String message) {
        super(new PrefixMessage("\\u001B[31m[WARN]\\u001B[0m"), message);
    }
}
