package common.view.console;

import server.type.Notice;

/**
 * notice console message 의 prefix 의 message
 *
 * prefix message 를 String 대신 PrefixNoticeConsoleFormat 을 다루는 이유.
 * 1. 확정적이고 정해진 포멧으로 prefix 의 규칙에 대해서 다룰수 있다.
 * 2. class 네이밍을 통해 명시적으로 문맥 설명이 가능하다. NoticeConsoleFormat 에 prefix 가 있다는 것을 class 로서 다룰수 있다.
 *
 * TODO
 * view 라는 맥락에서만 사용되어야하는 class
 * 1. string 을 instance variable 로 받아야할까.
 * 2. Cmd 을 instance variable 로 받아야할까.
 *
 */
public class PrefixNoticeConsoleMessage implements ConsoleMessage {
    private final String value;

    public PrefixNoticeConsoleMessage(String value) {
        this.value = value;
    }

    public static PrefixNoticeConsoleMessage create(Notice notice){
        String message = notice.getPrevColorCode() + notice.getSymbol() + notice.getPostColorCode();

        return new PrefixNoticeConsoleMessage(message);
    }

    @Override
    public String create() {
        return value;
    }
}
