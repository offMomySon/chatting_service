package common.command;

import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public enum Cmd {
    SEND("/send"), NOTICE("/notice");

    private final String value;

    Cmd(String value) {
        this.value = value;
    }

    public static Optional<Cmd> from(String sCmd) {
        return Arrays.stream(values())
            .filter(cmd -> StringUtils.equalsIgnoreCase(cmd.value, sCmd))
            .findAny();
    }
}
