package client.writer.console;

import client.message.console.ConsoleNoticeMessage;
import lombok.NonNull;

public class NoticeConsoleWriteStrategy extends ConsoleWriteStrategy {
    private final ConsoleNoticeMessage message;

    public NoticeConsoleWriteStrategy(@NonNull ConsoleNoticeMessage message) {
        this.message = message;
    }

    @Override
    public void write() {
        System.out.println(message.create());
    }
}
