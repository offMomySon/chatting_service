package command;

import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public enum Notice {
    INFO, WARN;

    public static Optional<Notice> from(String sNotice) {
        return Arrays.stream(values())
            .filter(notice -> StringUtils.equalsIgnoreCase(notice.name(), sNotice))
            .findAny();
    }
}
