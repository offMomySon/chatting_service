package server.v4;

import common.MessageOwner;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;

/**
 * 데이터 성격의 도메인 객체.
 * file message 에 time 정보를 포함 시킨다. (decorate 역할)
 */
public class FileMessage implements Message{
    private static final DateTimeFormatter MESSAGE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private final LocalDateTime time;

    private final MessageOwner owner;
    private final String message;

    public FileMessage(@NonNull LocalDateTime time, @NonNull MessageOwner owner, @NonNull String message) {
        this.time = time;
        this.owner = owner;
        this.message = message;
    }

    @Override
    public String createMessage() {
        return MessageFormat.format("{0} {1} {2}", MESSAGE_TIME_FORMATTER.format(time), owner.getValue(), message);
    }
}
