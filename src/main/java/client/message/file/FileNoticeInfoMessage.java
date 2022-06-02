package client.message.file;

import lombok.NonNull;
import server.protocal.notice.PrefixMessage;

public class FileNoticeInfoMessage extends FileNoticeMessage {
    public FileNoticeInfoMessage(@NonNull String message) {
        super(new PrefixMessage("[INFO]"), message);
    }
}
