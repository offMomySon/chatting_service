package common.view.file;

public class GenericFileMessage implements FileMessage {
    private final String message;

    public GenericFileMessage(String message) {
        this.message = message;
    }

    @Override
    public String create() {
        return message;
    }
}
