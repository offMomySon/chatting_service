package client.writer.console;

import client.message.console.ConsoleNoticeMessage;

public class ConsoleWriter {
    public void write(ConsoleNoticeMessage message) {
        System.out.println(message.create());
    }
}
