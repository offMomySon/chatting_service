package server.type;

import java.util.Arrays;

public enum Notice {
    INFO("\\u001B[33m", "[INFO]", "\\u001B[0m"),
    WARN("\\u001B[31m", "[WARN]", "\\u001B[0m");

    private final String prevColorCode;
    private final String symbol;

    public String getPrevColorCode() {
        return prevColorCode;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPostColorCode() {
        return postColorCode;
    }

    private final String postColorCode;

    Notice(String prevColorCode, String symbol, String postColorCode) {
        this.prevColorCode = prevColorCode;
        this.symbol = symbol;
        this.postColorCode = postColorCode;
    }

    private static boolean isExist(String cmd){
        return Arrays.stream(values())
            .anyMatch(noticeType -> noticeType.name().equalsIgnoreCase(cmd));
    }

    public static boolean notExist(String cmd){
        return !isExist(cmd);
    }

}
