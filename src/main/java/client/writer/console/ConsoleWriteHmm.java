package client.writer.console;

import client.message.console.ConsoleMessage;
import client.writer.Writer;
import lombok.NonNull;

public class ConsoleWriteHmm implements Writer {
    private final ConsoleWriter writer;
    private final ConsoleMessage message;

    public ConsoleWriteHmm(@NonNull ConsoleWriter writer, @NonNull ConsoleMessage message) {
        this.writer = writer;
        this.message = message;
    }

    @Override
    public void write() {
        writer.write(message);
    }
}
