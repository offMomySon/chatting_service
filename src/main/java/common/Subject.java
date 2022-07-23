package common;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum Subject {
    SERVER("[서버]"), CLIENT("[클라]"), INFO("[INFO]"), WARN("[WARN]");

    private static final String MESSAGE_FORMAT = "{0} {1}";
    private final String prefix;

    Subject(String prefix) {
        this.prefix = prefix;
    }

    public String with(@NonNull String message) {
        if(StringUtils.isBlank(message)){
            throw new RuntimeException("message is blank.");
        }
        if(StringUtils.isEmpty(message)){
            throw new RuntimeException("message is empty.");
        }

        return MessageFormat.format(MESSAGE_FORMAT, prefix, message);
    }

}
