package client.writer.console;

import client.message.console.ConsoleNoticeMessage;
import client.writer.Writer;
import lombok.NonNull;

public class ConsoleWriteStrategy implements Writer {
    private final ConsoleWriter writer;
    private final ConsoleNoticeMessage message;

    public ConsoleWriteStrategy(@NonNull ConsoleWriter writer, @NonNull ConsoleNoticeMessage message) {
        this.writer = writer;
        this.message = message;
    }

    @Override
    public void write() {
        writer.write(message);
    }
}
