package common.protocal.generic;

import common.protocal.SimpleMessageFormat;
import java.text.MessageFormat;
import lombok.NonNull;

public class GenericSimpleMessageFormat implements SimpleMessageFormat {
    private final String message;

    public GenericSimpleMessageFormat(@NonNull String message) {
        this.message = message;
    }

    public String createMessage() {
        return MessageFormat.format("0:{0}", message);
    }
}
