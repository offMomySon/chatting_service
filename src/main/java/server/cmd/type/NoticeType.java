package server.cmd.type;

import java.text.MessageFormat;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public enum NoticeType {
    INFO("info", "\\u001B[33m", "[INFO]", "\\u001B[33m"),
    WARN("warn", "\\u001B[0m",  "[WARN]","\\u001B[33m");

    private static final String MSG_DECORATOR= "{0}{1}{2} {3}";

    private final String name;
    private final String prevEncoding;
    private final String tag;
    private final String postEncoding;

    NoticeType(String name, String prevEncoding, String tag, String postEncoding) {
        this.name = name;
        this.prevEncoding = prevEncoding;
        this.tag = tag;
        this.postEncoding = postEncoding;
    }

    public boolean isEqual(String noticeType){
        return StringUtils.equalsIgnoreCase(this.name, noticeType);
    }

    public boolean notEqual(String noticeType){
        return !isEqual(noticeType);
    }

    public static boolean contain(String noticeType){
        return Arrays.stream(values())
            .anyMatch(nt -> nt.isEqual(noticeType));
    }

    public static boolean notContain(String noticeType){
        return !contain(noticeType);
    }

    public static NoticeType find(String noticeType){
        return Arrays.stream(values())
            .filter(nt -> nt.name.equals(noticeType))
            .findAny()
            .orElseThrow(()->new RuntimeException("Not exist matched notice type."));
    }

    public String getTag() {
        return tag;
    }

    public String getPrevEncoding() {
        return prevEncoding;
    }

    public String getPostEncoding() {
        return postEncoding;
    }

    public String decorate(String msg){
        return MessageFormat.format(MSG_DECORATOR, prevEncoding, tag, postEncoding, msg);
    }
}
