package client.message.file;

import lombok.NonNull;
import server.protocal.notice.PrefixMessage;

public class FileNoticeWarnMessage extends FileNoticeMessage {
    public FileNoticeWarnMessage(@NonNull String message) {
        super(new PrefixMessage("[WARN]"), message);
    }
}
