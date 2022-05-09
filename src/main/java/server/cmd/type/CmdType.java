package server.cmd.type;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public enum CmdType {
    SEND("send", 0), NOTICE("notice", 1);

    private static final int GENERAL_VALUE = 0;
    private static final int NOTICE_VALUE = 1;

    private final String name;
    private final int value;

    CmdType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public boolean isEqual(String cmdType){
        return StringUtils.equalsIgnoreCase(this.name, cmdType);
    }

    public boolean notEqual(String cmdType){
        return !isEqual(cmdType);
    }

    public static CmdType find(String cmdType){
        return Arrays.stream(values())
            .filter(ct -> ct.equals(cmdType))
            .findAny()
            .orElseThrow(()->new RuntimeException("Not exist cmd type."));
    }

    private static boolean isExistGeneralType(String sCmdType){
        return Arrays.stream(CmdType.values())
            .filter(CmdType::isGeneralType)
            .anyMatch(ct -> ct.name.equals(sCmdType));
    }

    public static boolean notExistGeneralType(String sCmdType){
        return !isExistGeneralType(sCmdType);
    }

    private static boolean isExistNoticeType(String sCmdType){
        return Arrays.stream(CmdType.values())
            .filter(CmdType::isNoticeType)
            .anyMatch(ct -> ct.name.equals(sCmdType));
    }

    public static boolean notExistNoticeType(String sCmdType){
        return !isExistNoticeType(sCmdType);
    }

    public int getValue() {
        return value;
    }

    public boolean isGeneralType(){
        return GENERAL_VALUE == value;
    }

    public boolean isNoticeType(){
        return NOTICE_VALUE == value;
    }
}
