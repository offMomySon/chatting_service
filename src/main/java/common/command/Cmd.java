package common.command;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum Cmd {
    SEND("/send", 0), NOTICE("/notice", 1);

    private final String value;
    private final int code;

    Cmd(String value, int code) {
        this.value = value;
        this.code = code;
    }

    public static Optional<Cmd> from(String sCmd) {
        return Arrays.stream(values())
            .filter(cmd -> StringUtils.equalsIgnoreCase(cmd.value, sCmd))
            .findAny();
    }

    public static Optional<Cmd> fromCode(int givenCode){
        return Arrays.stream(values())
            .filter(cmd-> cmd.getCode() == givenCode)
            .findAny();
    }
}
