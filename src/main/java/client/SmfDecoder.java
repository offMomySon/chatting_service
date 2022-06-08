package client;

import client.message.console.ConsoleNoticeInfoMessage;
import client.message.console.ConsoleNoticeWarnMessage;
import client.message.file.FileMessage;
import client.writer.console.ConsoleNotWriteStrategy;
import client.writer.console.ConsoleSmfWriteStrategy;
import client.writer.console.ConsoleWriteStrategy;
import client.writer.file.FileOwnerWriter;
import client.writer.file.FileWriter;
import client.writer.file.FileWriterInterface;
import client.writer.file.TimeNameFile;
import java.time.LocalDateTime;
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

    private final FileWriterInterface fileWriter;
    private final FileMessage fileMessage;
    private final ConsoleWriteStrategy consoleWriteStrategy;

    private SmfDecoder(@NonNull FileOwnerWriter fileWriter, @NonNull FileMessage fileMessage, @NonNull ConsoleWriteStrategy consoleWriteStrategy) {
        this.fileWriter = fileWriter;
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
            return new SmfDecoder(new FileOwnerWriter("SERVER", FileWriter.create(TimeNameFile.create(LocalDateTime.now()))), new FileMessage(message), new ConsoleNotWriteStrategy());
        }

        if (StringUtils.equals(code, NOTICE_CODE) && StringUtils.equals(prefix, NOTICE_INFO_PREFIX)) {
            return new SmfDecoder(new FileOwnerWriter("INFO", FileWriter.create(TimeNameFile.create(LocalDateTime.now()))), new FileMessage(message), new ConsoleSmfWriteStrategy(new ConsoleNoticeInfoMessage(message)));
        }

        if (StringUtils.equals(code, NOTICE_CODE) && StringUtils.equals(prefix, NOTICE_WARN_PREFIX)) {
            return new SmfDecoder(new FileOwnerWriter("WARN", FileWriter.create(TimeNameFile.create(LocalDateTime.now()))), new FileMessage(message), new ConsoleSmfWriteStrategy(new ConsoleNoticeWarnMessage(message)));
        }

        throw new RuntimeException("올바른 simpleMessage 가 아닙니다.");
    }

}
