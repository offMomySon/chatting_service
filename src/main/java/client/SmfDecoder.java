package client;

import client.message.console.ConsoleNoticeInfoMessage;
import client.message.console.ConsoleNoticeWarnMessage;
import client.message.file.FileMessage;
import client.writer.CompositedWriter;
import client.writer.console.ConsoleWriteStrategy;
import client.writer.console.ConsoleWriter;
import client.writer.file.BasicFileWriter;
import client.writer.file.FileOwnerWriter;
import client.writer.file.FileWriteStrategy;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import static common.MessageOwner.INFO;
import static common.MessageOwner.SERVER;
import static common.MessageOwner.WARN;

@Getter
public class SmfDecoder {
    private static final String DELIMITER = " :";

    private static final String GENERIC_CODE = "0";
    private static final String NOTICE_CODE = "1";

    private static final String NOTICE_INFO_PREFIX = "\\u001B[33m[INFO]\\u001B[0m";
    private static final String NOTICE_WARN_PREFIX = "\\u001B[31m[WARN]\\u001B[0m";

    private final CompositedWriter writer;

    private SmfDecoder(CompositedWriter writer) {
        this.writer = writer;
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
            FileWriteStrategy fileWriteStrategy = new FileWriteStrategy(new FileOwnerWriter(SERVER, BasicFileWriter.create(LocalDateTime.now())), new FileMessage(message));
            CompositedWriter writer = new CompositedWriter(List.of(fileWriteStrategy));

            return new SmfDecoder(writer);
        }

        if (StringUtils.equals(code, NOTICE_CODE) && StringUtils.equals(prefix, NOTICE_INFO_PREFIX)) {
            FileWriteStrategy fileWriteStrategy = new FileWriteStrategy(new FileOwnerWriter(INFO, BasicFileWriter.create(LocalDateTime.now())), new FileMessage(message));
            ConsoleWriteStrategy consoleWriteStrategy = new ConsoleWriteStrategy(new ConsoleWriter(), new ConsoleNoticeInfoMessage(message));
            CompositedWriter writer = new CompositedWriter(List.of(fileWriteStrategy, consoleWriteStrategy));

            return new SmfDecoder(writer);
        }

        if (StringUtils.equals(code, NOTICE_CODE) && StringUtils.equals(prefix, NOTICE_WARN_PREFIX)) {
            FileWriteStrategy fileWriteStrategy = new FileWriteStrategy(new FileOwnerWriter(WARN, BasicFileWriter.create(LocalDateTime.now())), new FileMessage(message));
            ConsoleWriteStrategy consoleWriteStrategy = new ConsoleWriteStrategy(new ConsoleWriter(), new ConsoleNoticeWarnMessage(message));
            CompositedWriter writer = new CompositedWriter(List.of(fileWriteStrategy, consoleWriteStrategy));

            return new SmfDecoder(writer);
        }

        throw new RuntimeException("올바른 simpleMessage 가 아닙니다.");
    }

}
