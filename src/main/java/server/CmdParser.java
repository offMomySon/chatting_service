package server;

import command.Cmd;
import command.Notice;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import server.message.SimpleMessageFormat;
import server.message.generic.GenericSimpleMessageFormat;
import server.message.notice.NoticeInfoSimpleMessageFormat;
import server.message.notice.NoticeWarnSimpleMessageFormat;
import server.writer.file.FileAllWriteStrategy;
import server.writer.file.FileIpWriteStrategy;
import server.writer.file.FileWriteStrategy;
import server.writer.file.FileWriter;
import server.writer.smf.SmfAllSendStrategy;
import server.writer.smf.SmfIpSendStrategy;
import server.writer.smf.SmfSendStrategy;
import server.writer.smf.SmfSender;

public class CmdParser {
    private static final Set<String> ALL_ADDRESS = Set.of("*", "ALL");

    private final SmfSendStrategy smfSendStrategy;
    private final SimpleMessageFormat simpleMessageFormat;
    private final FileWriteStrategy fileWriteStrategy;
    private final String message;

    private CmdParser(@NonNull SmfSendStrategy smfSendStrategy, @NonNull SimpleMessageFormat simpleMessageFormat, @NonNull FileWriteStrategy fileWriteStrategy, @NonNull String message) {
        this.smfSendStrategy = smfSendStrategy;
        this.simpleMessageFormat = simpleMessageFormat;
        this.fileWriteStrategy = fileWriteStrategy;
        this.message = message;
    }

    public static CmdParser parse(@NonNull String sCmd, @NonNull SmfSender smfSender, @NonNull FileWriter fileWriter) {
        SmfSendStrategy smfSendStrategy = null;
        SimpleMessageFormat simpleMessageFormat = null;
        FileWriteStrategy fileWriteStrategy = null;

        Deque<String> queue = new ArrayDeque<>(List.of(sCmd.split(" ")));

        Cmd cmd = Cmd.from(queue.poll()).orElseThrow(()-> new RuntimeException("일치하는 cmd 가 존재 하지 않습니다."));
        Notice notice = null;
        if(cmd == Cmd.NOTICE){
             notice = Notice.from(queue.poll()).orElseThrow(()-> new RuntimeException("일치하는 notice 가 존재하지 않습니다."));
        }
        String[] sAddresses = queue.poll().split(",");
        String message = String.join(" ", queue.poll());

        String fileWriterOwner = createFileWriterOwner(notice, cmd);
        simpleMessageFormat = createSimpleMessageFormat(message, notice, cmd);

        if(isAllAddressContain(sAddresses)){
            smfSendStrategy = new SmfAllSendStrategy(smfSender);
            fileWriteStrategy = new FileAllWriteStrategy(fileWriter, fileWriterOwner);

            return new CmdParser(smfSendStrategy, simpleMessageFormat, fileWriteStrategy, message);
        }

        List<Address> addresses = createAddresses(sAddresses);

        smfSendStrategy = new SmfIpSendStrategy(addresses, smfSender);
        fileWriteStrategy = new FileIpWriteStrategy(addresses, fileWriter, fileWriterOwner);

        return new CmdParser(smfSendStrategy, simpleMessageFormat, fileWriteStrategy, message);
    }

    @NotNull
    private static SimpleMessageFormat createSimpleMessageFormat(String message, Notice notice, Cmd cmd) {
        if(cmd == Cmd.SEND){
            return new GenericSimpleMessageFormat(message);
        }

        if(cmd == Cmd.NOTICE){
            switch (notice){
                case INFO:
                    return new NoticeInfoSimpleMessageFormat(message);
                case WARN:
                    return new NoticeWarnSimpleMessageFormat(message);
            }
        }

        throw new RuntimeException("not exist notice cmd");
    }

    @NotNull
    private static String createFileWriterOwner(Notice notice, Cmd cmd) {
        if(cmd == Cmd.SEND){
            return "서버";
        }

        if(cmd == Cmd.NOTICE){
            switch (notice){
                case INFO:
                    return "INFO";
                case WARN:
                    return "WARN";
            }
        }

        throw new RuntimeException("not exist notice cmd");
    }

    @NotNull
    private static List<Address> createAddresses(String[] sAddresses) {
        return Stream.of(sAddresses).map(Address::new).collect(Collectors.toUnmodifiableList());
    }

    private static boolean isAllAddressContain(String[] sAddresses) {
        return Arrays.stream(sAddresses).anyMatch(sAddress -> ALL_ADDRESS.contains(StringUtils.upperCase(sAddress)));
    }
}
