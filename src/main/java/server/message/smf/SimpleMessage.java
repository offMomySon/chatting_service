package server.message.smf;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import server.message.Message;

public abstract class SimpleMessage implements Message {
    private final String value;

    protected SimpleMessage(@NonNull String value) {
        this.value = validate(value);
    }

    protected static String validate(String message){
        if(StringUtils.isBlank(message)){
            throw new RuntimeException("message is blank.");
        }
        if(StringUtils.isEmpty(message)){
            throw new RuntimeException("message is empty.");
        }
        return message;
    }

    @Override
    public String create() {
        return value;
    }
}
