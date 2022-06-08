package client.message.file;

import lombok.NonNull;

public class FileMessage {
    private final String message;

    public FileMessage(@NonNull String message) {
        this.message = message;
    }

    public String create() {
        return message;
    }
}
