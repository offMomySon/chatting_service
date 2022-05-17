package common.protocol;

import common.view.console.ConsoleMessage;
import common.view.console.NoticeInfoConsoleMessage;
import common.view.console.NoticeWarnConsoleMessage;
import common.view.file.FileMessage;
import common.view.file.NoticeInfoFileMessage;
import common.view.file.NoticeWarnFileMessage;
import server.type.Cmd;
import server.type.Notice;

public class NoticeSimpleMessageFormat implements SimpleMessageFormat{
    private static final String NOTICE_DELIMITER = " ";

    private final Cmd cmd;
    private final Notice notice;
    private final String message;

    public NoticeSimpleMessageFormat(Cmd cmd, Notice notice, String message) {
        this.cmd = cmd;
        this.notice = notice;
        this.message = message;
    }

    /**
     * NoticeInfoConsoleFormat 데이터 생성 방법을 2가지로 분리 할 수 있을 것 같다.
     *
     * 1. 데이터 비교를 통해 구체적인 객체를 생성한다.
     * (ex) NoticeInfoConsoleMessage 클래스 생성.
     * Info 정보를 담은 notice console message 를 생성할 수 있다.
     *
     * 장점.
     * 코드로 Notic 데이터에 따라 다른 view 를 생성한다는 것을 알 수 있고,
     * 객체 생성 방법을 보면 명시적으로 prefix 가 필요하다는것을 알 릴 수 있다.
     *
     * (생성 방법 ex)
     * public NoticeInfoConsoleMessage(String message) {
     *     super(PrefixNoticeConsoleMessage.create(Notice.INFO), message);
     * }
     *
     * 2. 데이터를 모두 포괄하는 비교적 덜 구체적인 객체를 생성한다
     * 위의 맥락을 숨긴 단순한 객체를 생성하게 된다.
     * Console 에 Notice 메세지를 생성한다는 맥락만 전달 할 수 있다.
     * (ex) NoticeConsoleMessage
     * (생성 방법 ex)
     * public NoticeonsoleMessage( Notice type ,String message) {
     *     this.type = type;
     *     this.message = message;
     * }
     *
     * 생각.
     * 논리가 없는 view 객체라서, 구체적이고 명시적일 수록 좋을까?
     * -> 구체적이고 명시적일 수록 좋을 것 같다. 어떻게 구성되는지 각각의 view 마다 명시적인 요구 사항을 담을 수 있기 때문에.
     * -> NoticeInfoConsoleMessage 객체는 view 를 구성하기위해 info prefix 가 필요하다 는 것을 담고 있다.
     *
     * 아래의 맥락에서 생각해보면,
     * 맥락 - 현재 요구사항을 포괄하는 코드, 시스템을 만드는 것이 좋다, 요구되지 않은 사항까지 포괄할 필요는 없다? ( 정확한 말씀이 기억이 안나네요.. )
     *
     * 현재 요구사항만 생각해 본다면 prefix 객체를 생성하지 않은 NoticeConsoleMessage 만으로 충분하다.
     * notice type 에 따라 아래 메세지를 생성할 수 있다.
     * 하지만 view 가 어떻게 구성되는지 명시적이지 못하기 때문에 해석 비용이 들어간다.
     * 해석 비용이 들어가기 때문에 prefix 객체를 생성하는 것이 좋다.
     *
     * info 이면, 콘솔에 "\u001B[33m[INFO]\u001B[0m This is Info" 출력
     * warn 이면, 콘솔에 "\u001B[31m[WARN]\u001B[0m This is Warn" 출력
     *
     * 해석 비용을 줄이기 위해 view 에서만 사용되는 객체를 만든다고 생각하면 될까?
     */
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
