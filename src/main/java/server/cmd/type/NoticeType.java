package server.cmd.type;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public enum NoticeType {
    INFO("info", "[INFO]", "\\u001B[33m", "\\u001B[33m"),
    WARN("warn", "[WARN]", "\\u001B[0m", "\\u001B[33m");

    private final String name;
    private final String tag;
    private final String prevEncoding;
    private final String postEncoding;

    NoticeType(String name, String tag, String prevEncoding, String postEncoding) {
        this.name = name;
        this.tag = tag;
        this.prevEncoding = prevEncoding;
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
}
