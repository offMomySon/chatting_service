package server.writer;

import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public enum Usage {
    SOCKET,FILE;

    public static Optional<Usage> find(String usage){
        return Arrays.stream(values())
            .filter(u-> StringUtils.equalsIgnoreCase(u.name(),usage))
            .findAny();
    }
}
