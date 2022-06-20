package server.message.file;

import common.MessageOwner;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import server.v5.Message;

/**
 * 데이터 성격의 도메인 객체.
 * file message 에 time 정보를 포함 시킨다. (decorate 역할)
 */
public class FileMessage implements Message {
    private static final DateTimeFormatter MESSAGE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private final LocalDateTime dateTime;

    private final MessageOwner owner;
    private final String message;

    public FileMessage(@NonNull LocalDateTime dateTime, @NonNull MessageOwner owner, @NonNull String message) {
        this.dateTime = dateTime;
        this.owner = owner;
        this.message = message;
    }

    @Override
    public String create() {
        return MessageFormat.format("{0} {1} {2}", MESSAGE_TIME_FORMATTER.format(dateTime), owner.getValue(), message);
    }
}
