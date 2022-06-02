package client.message.file;

import lombok.NonNull;

public class FileGenericMessage implements FileMessage {
    private final String message;

    public FileGenericMessage(@NonNull String message) {
        this.message = message;
    }

    @Override
    public String create() {
        return message;
    }
}
