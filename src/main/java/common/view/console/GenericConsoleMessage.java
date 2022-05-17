package common.view.console;

public class GenericConsoleMessage implements ConsoleMessage {
    private final String message;

    public GenericConsoleMessage(String message) {
        this.message = message;
    }

    @Override
    public String create() {
        return message;
    }
}
