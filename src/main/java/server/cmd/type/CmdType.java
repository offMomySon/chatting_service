package server.cmd.type;

import java.util.Arrays;
import java.util.Optional;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public enum CmdType {
    SEND(0,"send"), NOTICE(1, "notice");

    private final int value;
    private final String name;

    CmdType(int value, String name) {
        this.value = value;
        this.name = name;
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
            .orElseThrow(()->new RuntimeException("일치하는 타입이 없습니다."));
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
