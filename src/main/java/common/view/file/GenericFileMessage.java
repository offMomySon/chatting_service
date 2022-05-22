package common.view.file;

/**
 * generic 메세지를 file 에 출력하기 위한 view 객체.
 */
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
