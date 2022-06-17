package server.message.smf.generic;

import java.text.MessageFormat;
import lombok.NonNull;
import server.message.smf.SimpleMessageFormat;

public class GenericSimpleMessageFormat implements SimpleMessageFormat {
    private final String message;

    public GenericSimpleMessageFormat(@NonNull String message) {
        this.message = message;
    }

    @Override
    public String create() {
        return MessageFormat.format("0:{0}", message);
    }
}
