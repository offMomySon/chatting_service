package client;

import client.message.console.ConsoleMessage;
import client.message.file.FileMessage;
import client.writer.CompositedWriter;
import client.writer.console.ConsoleWriteStrategy;
import client.writer.console.ConsoleWriter;
import client.writer.file.BasicFileWriter;
import client.writer.file.FileOwnerWriter;
import client.writer.file.FileWriteStrategy;
import common.command.Cmd;
import common.command.Notice;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.math.NumberUtils;
import static common.MessageOwner.INFO;
import static common.MessageOwner.SERVER;
import static common.MessageOwner.WARN;
import static common.command.Cmd.NOTICE;

@Getter
public class SmfDecoder {
    private static final String DELIMITER = " :";

    private final CompositedWriter writer;

    private SmfDecoder(CompositedWriter writer) {
        this.writer = writer;
    }

    public static SmfDecoder decode(String simpleMessage){
        Deque<String> deque = new ArrayDeque<>(List.of(simpleMessage.split(DELIMITER)));

        String code = deque.poll();
        String prefix = null;

        Cmd cmd = Cmd.fromCode(Integer.parseInt(code)).orElseThrow(()-> new RuntimeException("일치하는 cmd 가 없습니다."));
        if (cmd == NOTICE) {
            prefix = deque.poll();
        }
        String message = String.join(" ", deque);

        if(!NumberUtils.isParsable(code)){
            throw new RuntimeException("int 로 변환가능한 숫자가 아닙니다.");
        }

        if(cmd == Cmd.SEND){
            FileWriteStrategy fileWriteStrategy = new FileWriteStrategy(new FileOwnerWriter(SERVER, BasicFileWriter.create(LocalDateTime.now())), new FileMessage(message));
            CompositedWriter writer = new CompositedWriter(List.of(fileWriteStrategy));
            return new SmfDecoder(writer);
        }

        if (cmd == NOTICE){
            Notice notice = Notice.fromPrefix(prefix).orElseThrow(()-> new RuntimeException("일치하는 notice 가 없습니다."));
            FileMessage fileMessage = new FileMessage(message);
            BasicFileWriter basicFileWriter = BasicFileWriter.create(LocalDateTime.now());
            ConsoleWriteStrategy consoleWriteStrategy = new ConsoleWriteStrategy(new ConsoleWriter(), new ConsoleMessage(message));

            if(notice == Notice.INFO){
                FileWriteStrategy fileWriteStrategy = new FileWriteStrategy(new FileOwnerWriter(INFO, basicFileWriter), fileMessage);

                CompositedWriter writer = new CompositedWriter(List.of(fileWriteStrategy, consoleWriteStrategy));
                return new SmfDecoder(writer);
            }

            if(notice == Notice.WARN){
                FileWriteStrategy fileWriteStrategy = new FileWriteStrategy(new FileOwnerWriter(WARN, basicFileWriter), fileMessage);

                CompositedWriter writer = new CompositedWriter(List.of(fileWriteStrategy, consoleWriteStrategy));
                return new SmfDecoder(writer);
            }
        }

        throw new RuntimeException("변환가능한 cmd 타입이 아닙니다.");
    }
}
