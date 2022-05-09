package server.cmd.type;

import java.util.Arrays;
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

    public boolean isEqual(String cmd){
        return StringUtils.equalsIgnoreCase(this.cmd, cmd);
    }

    public boolean notEqual(String cmd){
        return !isEqual(cmd);
    }

    public static CmdType find(String cmd){
        return Arrays.stream(values())
            .filter(ct -> StringUtils.equalsIgnoreCase(ct.cmd, cmd))
            .findAny()
            .orElseThrow(()->new RuntimeException("Not exist cmd type."));
    }

    private static boolean isExistGeneralType(String sCmd){
        return Arrays.stream(CmdType.values())
            .filter(CmdType::isGeneralType)
            .anyMatch(ct -> ct.cmd.equals(sCmd));
    }

    public static boolean notExistGeneralType(String sCmd){
        return !isExistGeneralType(sCmd);
    }

    private static boolean isExistNoticeType(String sCmd){
        return Arrays.stream(CmdType.values())
            .filter(CmdType::isNoticeType)
            .anyMatch(ct -> ct.cmd.equals(sCmd));
    }

    public static boolean notExistNoticeType(String sCmd){
        return !isExistNoticeType(sCmd);
    }

    public int getCode() {
        return code;
    }

    private boolean isGeneralType(){
        return GENERAL_VALUE == code;
    }

    private boolean isNoticeType(){
        return NOTICE_VALUE == code;
    }
}
