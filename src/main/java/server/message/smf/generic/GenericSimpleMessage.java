package server.message.smf.generic;

import java.text.MessageFormat;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import server.message.smf.SimpleMessage;

public class GenericSimpleMessage extends SimpleMessage {
    private static final int code = 0;
    public GenericSimpleMessage(@NonNull String message) {
        super(MessageFormat.format("{0}:{1}", code, validate(message)));
    }
}
