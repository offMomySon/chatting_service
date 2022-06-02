package client.writer.console;

import client.message.console.ConsoleNoticeMessage;
import lombok.NonNull;

public class ConsoleSmfWriteStrategy extends ConsoleWriteStrategy {
    private final ConsoleNoticeMessage message;

    public ConsoleSmfWriteStrategy(@NonNull ConsoleNoticeMessage message) {
        this.message = message;
    }

    @Override
    public void write() {
        System.out.println(message.create());
    }
}
