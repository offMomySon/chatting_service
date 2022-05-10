package server.cmd.type;

import java.util.Arrays;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;

public enum CmdType {
    SEND("send", 0), NOTICE("notice", 1);

    private static final int GENERAL_VALUE = 0;
    private static final int NOTICE_VALUE = 1;

    private final String cmd;
    private final int code;

    CmdType(String cmd, int code) {
        this.cmd = cmd;
        this.code = code;
    }

    public static CmdType findGeneralType(String cmd){
        return filteredAnyFind(CmdType::isGeneralType,cmd);
    }

    public static CmdType findNoticeType(String cmd){
        return filteredAnyFind(CmdType::isNoticeType,cmd);
    }

    private static CmdType filteredAnyFind(Predicate<CmdType> predicate, String cmd) {
        return Arrays.stream(values())
            .filter(predicate)
            .filter(ct -> StringUtils.equalsIgnoreCase(ct.cmd, cmd))
            .findAny()
            .orElseThrow(()->new RuntimeException("Not exist cmd type."));
    }

    public static boolean isGeneralType(String sCmd){
        return filteredAnyMatch(CmdType::isGeneralType, sCmd);
    }

    public static boolean isExistNoticeType(String sCmd){
        return filteredAnyMatch(CmdType::isNoticeType, sCmd);
    }

    public static boolean notExistGeneralType(String sCmd){
        return !isGeneralType(sCmd);
    }

    public static boolean notExistNoticeType(String sCmd){
        return !isExistNoticeType(sCmd);
    }

    public static boolean filteredAnyMatch(Predicate<CmdType> predicate, String sCmd){
        return Arrays.stream(CmdType.values())
            .filter(predicate)
            .anyMatch(ct -> ct.cmd.equals(sCmd));
    }

    public int getCode() {
        return code;
    }

    public boolean isGeneralType(){
        return GENERAL_VALUE == code;
    }

    public boolean isNoticeType(){
        return NOTICE_VALUE == code;
    }
}
