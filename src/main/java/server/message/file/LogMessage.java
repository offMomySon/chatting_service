package server.message.file;

import common.MessageOwner;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import server.v5.Message;

/**
 * 역할.
 * 타임스탬프를 남기는 로그형 메세지를 생성합니다.(=> view 역할)
 */
public class LogMessage implements Message {
    private static final DateTimeFormatter MESSAGE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private final String value;

    private LogMessage(@NonNull String value) {
        this.value = value;
    }

    public static LogMessage of(@NonNull LocalDateTime dateTime, @NonNull MessageOwner owner, @NonNull String message) {
        if(StringUtils.isBlank(message)){
            throw new RuntimeException("message is blank.");
        }
        if(StringUtils.isEmpty(message)){
            throw new RuntimeException("message is empty.");
        }

        String value = MessageFormat.format("{0} {1} {2}", MESSAGE_TIME_FORMATTER.format(dateTime), owner.getValue(), message);
        return new LogMessage(value);
    }

    @Override
    public String create() {
        return value;
    }
}
