package server.message.smf.generic;

import java.text.MessageFormat;
import lombok.NonNull;
import server.message.smf.SimpleMessage;

public class GenericSimpleMessage extends SimpleMessage {
    public GenericSimpleMessage(@NonNull String message) {
        super(MessageFormat.format("{0}:{1}", 0, validate(message)));
    }
}
