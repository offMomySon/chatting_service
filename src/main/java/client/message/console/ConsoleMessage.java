package client.message.console;

import lombok.NonNull;

public class ConsoleMessage {
    private final String value;

    public ConsoleMessage(@NonNull String value) {
        this.value = value;
    }

    public String create() {
        return value;
    }
}
