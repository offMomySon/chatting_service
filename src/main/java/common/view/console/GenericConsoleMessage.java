package common.view.console;

/**
 * generic 메세지를 console 에 출력하기 위한 view 객체.
 */
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
