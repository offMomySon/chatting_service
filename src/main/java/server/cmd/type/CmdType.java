package server.cmd.type;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public enum CmdType {
    SEND("send",0), NOTICE("notice", 1);

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

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
