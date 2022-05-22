package common.view.format;

import common.view.console.ConsoleMessage;
import common.view.console.GenericConsoleMessage;
import common.view.file.FileMessage;
import common.view.file.GenericFileMessage;

/**
 * 용도 별 generic view 를 생성하기 위한, 헬퍼 view 객체
 */
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
