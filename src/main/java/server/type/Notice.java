package server.type;

import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public enum Notice {
    INFO("\\u001B[33m", "[INFO]", "\\u001B[0m"),
    WARN("\\u001B[31m", "[WARN]", "\\u001B[0m");

    private final String prevColorCode;
    private final String symbol;

    private final String postColorCode;

    Notice(String prevColorCode, String symbol, String postColorCode) {
        this.prevColorCode = prevColorCode;
        this.symbol = symbol;
        this.postColorCode = postColorCode;
    }

    public static Optional<Notice> from(String sNotice){
        return Arrays.stream(values())
            .filter(notice -> StringUtils.equalsIgnoreCase(notice.name(), sNotice))
            .findAny();
    }

    private static boolean isExist(String cmd){
        return Arrays.stream(values())
            .anyMatch(noticeType -> noticeType.name().equalsIgnoreCase(cmd));
    }

    public static boolean notExist(String cmd){
        return !isExist(cmd);
    }

    public static Notice getNotice(String cmd){
        return Arrays.stream(values())
            .filter(n-> StringUtils.equalsIgnoreCase(n.name(), cmd))
            .findAny()
            .orElseThrow(()-> new RuntimeException("Fail to find notice cmd"));
    }
}
