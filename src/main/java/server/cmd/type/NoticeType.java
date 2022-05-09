package server.cmd.type;

import java.text.MessageFormat;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public enum NoticeType {
    INFO("info", "\\u001B[33m", "[INFO]", "\\u001B[33m"),
    WARN("warn", "\\u001B[0m",  "[WARN]","\\u001B[33m");

    private static final String MSG_DECORATOR= "{0}{1}{2} {3}";

    private final String cmd;
    private final String prevEncoding;
    private final String tag;
    private final String postEncoding;

    NoticeType(String cmd, String prevEncoding, String tag, String postEncoding) {
        this.cmd = cmd;
        this.prevEncoding = prevEncoding;
        this.tag = tag;
        this.postEncoding = postEncoding;
    }

    public boolean isEqual(String cmd){
        return StringUtils.equalsIgnoreCase(this.cmd, cmd);
    }

    public static boolean contain(String noticeType){
        return Arrays.stream(values())
            .anyMatch(nt -> nt.isEqual(noticeType));
    }

    public static boolean notContain(String cmd){
        return !contain(cmd);
    }

    public static NoticeType find(String cmd){
        return Arrays.stream(values())
            .filter(nt -> StringUtils.equalsIgnoreCase(nt.cmd, cmd))
            .findAny()
            .orElseThrow(()->new RuntimeException("Not exist matched notice type."));
    }

    private static boolean isExistType(String sCmd){
        return Arrays.stream(values())
            .anyMatch(cmdType -> StringUtils.equalsIgnoreCase(cmdType.cmd, sCmd));
    }

    public static boolean notExistType(String sCmd){
        return !isExistType(sCmd);
    }

    public String decorate(String msg){
        return MessageFormat.format(MSG_DECORATOR, prevEncoding, tag, postEncoding, msg);
    }
}
