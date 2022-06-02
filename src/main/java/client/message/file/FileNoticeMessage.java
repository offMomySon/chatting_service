package client.message.file;

import java.text.MessageFormat;
import lombok.NonNull;
import server.protocal.notice.PrefixMessage;

public abstract class FileNoticeMessage implements FileMessage {
    private final PrefixMessage prefix;
    private final String message;

    public FileNoticeMessage(@NonNull PrefixMessage prefix, @NonNull String message) {
        this.prefix = prefix;
        this.message = message;
    }


    @Override
    public String create() {
        return MessageFormat.format("{0} {1}", prefix.create(), message);
    }
}
