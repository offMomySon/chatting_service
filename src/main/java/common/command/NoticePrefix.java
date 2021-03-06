package common.command;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum NoticePrefix {
    INFO("\\u001B[33m[INFO]\\u001B[0m"), WARN("\\u001B[31m[WARN]\\u001B[0m");

    private final String value;

    NoticePrefix(String value) {
        this.value = value;
    }

    public String with(String message){
        return MessageFormat.format("{0} {1}", value, message);
    }
    public static Optional<NoticePrefix> from(String sNotice) {
        return Arrays.stream(values())
            .filter(notice -> StringUtils.equalsIgnoreCase(notice.name(), sNotice))
            .findAny();
    }

    public static Optional<NoticePrefix> fromPrefix(String givenPrefix){
        return Arrays.stream(values())
            .filter(notice -> StringUtils.equals(notice.getValue(), givenPrefix))
            .findAny();
    }
}
