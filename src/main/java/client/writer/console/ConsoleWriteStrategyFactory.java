package client.writer.console;

import client.message.console.ConsoleNoticeInfoMessage;
import client.message.console.ConsoleNoticeWarnMessage;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class ConsoleWriteStrategyFactory {
    private static final String DELIMITER = " :";

    private static final String GENERIC_CODE = "0";
    private static final String NOTICE_CODE = "1";

    private static final String NOTICE_INFO_PREFIX = "\\u001B[33m[INFO]\\u001B[0m";
    private static final String NOTICE_WARN_PREFIX = "\\u001B[31m[WARN]\\u001B[0m";


    public ConsoleWriteStrategy create(String simpleMessage) {
        Deque<String> deque = new ArrayDeque<>(List.of(simpleMessage.split(DELIMITER)));

        String code = deque.poll();
        String prefix = null;
        if (StringUtils.equals(code, NOTICE_CODE)) {
            prefix = deque.poll();
        }
        String message = String.join(" ", deque);

        if (StringUtils.equals(code, GENERIC_CODE)) {
            return new NotWriteConsoleWriteStrategy();
        }

        if (StringUtils.equals(code, NOTICE_CODE) && StringUtils.equals(prefix, NOTICE_INFO_PREFIX)) {
            return new NoticeConsoleWriteStrategy(new ConsoleNoticeInfoMessage(message));
        }

        if (StringUtils.equals(code, NOTICE_CODE) && StringUtils.equals(prefix, NOTICE_WARN_PREFIX)) {
            return new NoticeConsoleWriteStrategy(new ConsoleNoticeWarnMessage(message));
        }

        throw new RuntimeException("올바른 simpleMessage 가 아닙니다.");
    }
}
