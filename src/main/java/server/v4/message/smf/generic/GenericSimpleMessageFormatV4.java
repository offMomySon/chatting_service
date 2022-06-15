package server.v4.message.smf.generic;

import java.text.MessageFormat;
import lombok.NonNull;
import server.v4.message.smf.SimpleMessageFormatV4;

public class GenericSimpleMessageFormatV4 implements SimpleMessageFormatV4 {
    private final String message;

    public GenericSimpleMessageFormatV4(@NonNull String message) {
        this.message = message;
    }

    public String createMessage() {
        return MessageFormat.format("0:{0}", message);
    }
}
