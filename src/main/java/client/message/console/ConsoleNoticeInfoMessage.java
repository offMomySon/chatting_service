package client.message.console;

import lombok.NonNull;
import server.message.notice.PrefixMessage;

public class ConsoleNoticeInfoMessage extends ConsoleNoticeMessage {
    public ConsoleNoticeInfoMessage(@NonNull String message) {
        super(new PrefixMessage("\\u001B[33m[INFO]\\u001B[0m"), message);
    }
}
