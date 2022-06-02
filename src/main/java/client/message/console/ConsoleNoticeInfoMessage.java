package client.message.console;

import common.protocal.notice.PrefixMessage;
import lombok.NonNull;

public class ConsoleNoticeInfoMessage extends ConsoleNoticeMessage {
    public ConsoleNoticeInfoMessage(@NonNull String message) {
        super(new PrefixMessage("\\u001B[33m[INFO]\\u001B[0m"), message);
    }
}
