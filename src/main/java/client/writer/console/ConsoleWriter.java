package client.writer.console;

import client.message.console.ConsoleMessage;

public class ConsoleWriter {
    public void write(ConsoleMessage message) {
        System.out.println(message.create());
    }
}
