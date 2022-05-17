package common.protocol;

import common.view.console.ConsoleMessage;
import common.view.console.GenericConsoleMessage;
import common.view.file.FileMessage;
import common.view.file.GenericFileMessage;
import server.type.Cmd;

public class GenericSimpleMessageFormat implements SimpleMessageFormat{
    private final String message;

    public GenericSimpleMessageFormat(String message) {
        this.message = message;
    }

    @Override
    public ConsoleMessage createConsoleForm() {
        return new GenericConsoleMessage(message);
    }

    @Override
    public FileMessage createFileForm() {
        return new GenericFileMessage(message);
    }
}
