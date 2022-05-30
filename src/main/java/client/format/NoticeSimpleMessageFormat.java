package client.format;

import common.view.console.ConsoleMessage;
import common.view.console.NoticeInfoConsoleMessage;
import common.view.console.NoticeWarnConsoleMessage;
import common.view.file.FileMessage;
import common.view.file.NoticeInfoFileMessage;
import common.view.file.NoticeWarnFileMessage;
import server.type.Notice;

/**
 * 용도 별 generic view 를 생성하기 위한, 헬퍼 view 객체
 */
@Deprecated
public class NoticeSimpleMessageFormat implements SimpleMessageFormat{
    private final Notice notice;
    private final String message;

    public NoticeSimpleMessageFormat(Notice notice, String message) {
        this.notice = notice;
        this.message = message;
    }

    @Override
    public ConsoleMessage createConsoleForm() {
        if(notice == Notice.INFO){
            return new NoticeInfoConsoleMessage(message);
        }
        return new NoticeWarnConsoleMessage(message);
    }

    @Override
    public FileMessage createFileForm() {
        if(notice == Notice.INFO){
            return new NoticeInfoFileMessage(message);
        }
        return new NoticeWarnFileMessage(message);
    }
}
