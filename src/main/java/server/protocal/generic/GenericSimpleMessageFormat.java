package server.protocal.generic;

import java.io.FileOutputStream;
import java.text.MessageFormat;
import lombok.NonNull;

public class GenericSimpleMessageFormat {
    private final String message;

    public GenericSimpleMessageFormat(@NonNull String message) {
        this.message = message;
    }

    public String createMessage(){
        return MessageFormat.format("0:{0}", message);
    }
}
