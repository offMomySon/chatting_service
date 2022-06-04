package client;

import client.message.console.ConsoleNoticeInfoMessage;
import client.message.console.ConsoleNoticeWarnMessage;
import client.message.file.FileGenericMessage;
import client.message.file.FileMessage;
import client.message.file.FileNoticeInfoMessage;
import client.message.file.FileNoticeWarnMessage;
import client.writer.console.ConsoleNotWriteStrategy;
import client.writer.console.ConsoleSmfWriteStrategy;
import client.writer.console.ConsoleWriteStrategy;
import client.writer.file.FileOwnerWriter;
import client.writer.file.FileWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

@Getter
public class SmfDecoder {
    private static final String DELIMITER = " :";

    private static final String GENERIC_CODE = "0";
    private static final String NOTICE_CODE = "1";

    private static final String NOTICE_INFO_PREFIX = "\\u001B[33m[INFO]\\u001B[0m";
    private static final String NOTICE_WARN_PREFIX = "\\u001B[31m[WARN]\\u001B[0m";

    private final FileOwnerWriter fileOwnerWriter;
    private final FileMessage fileMessage;
    private final ConsoleWriteStrategy consoleWriteStrategy;

    private SmfDecoder(@NonNull FileOwnerWriter fileOwnerWriter, @NonNull FileMessage fileMessage, @NonNull ConsoleWriteStrategy consoleWriteStrategy) {
        this.fileOwnerWriter = fileOwnerWriter;
        this.fileMessage = fileMessage;
        this.consoleWriteStrategy = consoleWriteStrategy;
    }

    public static SmfDecoder decode(String simpleMessage){
        Deque<String> deque = new ArrayDeque<>(List.of(simpleMessage.split(DELIMITER)));

        String code = deque.poll();
        String prefix = null;
        if (StringUtils.equals(code, NOTICE_CODE)) {
            prefix = deque.poll();
        }
        String message = String.join(" ", deque);

        if (StringUtils.equals(code, GENERIC_CODE)) {
            return new SmfDecoder(new FileOwnerWriter("SERVER", new FileWriter()), new FileGenericMessage(message), new ConsoleNotWriteStrategy());
        }

        if (StringUtils.equals(code, NOTICE_CODE) && StringUtils.equals(prefix, NOTICE_INFO_PREFIX)) {
            return new SmfDecoder(new FileOwnerWriter("INFO", new FileWriter()), new FileNoticeInfoMessage(message), new ConsoleSmfWriteStrategy(new ConsoleNoticeInfoMessage(message)));
        }

        if (StringUtils.equals(code, NOTICE_CODE) && StringUtils.equals(prefix, NOTICE_WARN_PREFIX)) {
            return new SmfDecoder(new FileOwnerWriter("WARN", new FileWriter()), new FileNoticeWarnMessage(message), new ConsoleSmfWriteStrategy(new ConsoleNoticeWarnMessage(message)));
        }

        throw new RuntimeException("올바른 simpleMessage 가 아닙니다.");
    }

}
