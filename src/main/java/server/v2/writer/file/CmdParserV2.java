package server.v2.writer.file;

import common.command.Cmd;
import common.command.Notice;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import server.Address;
import server.message.SimpleMessageFormat;
import server.message.generic.GenericSimpleMessageFormat;
import server.message.notice.NoticeInfoSimpleMessageFormat;
import server.message.notice.NoticeWarnSimpleMessageFormat;
import server.writer.smf.SmfAllSendStrategy;
import server.writer.smf.SmfIpSendStrategy;
import server.writer.smf.SmfSendStrategy;
import server.writer.smf.SmfSender;

@Getter
public class CmdParserV2 {
    private static final Set<String> ALL_ADDRESS = Set.of("*", "ALL");
    private final SmfSendStrategy smfSendStrategy;
    private final SimpleMessageFormat simpleMessageFormat;
    private final FileWritersV2 fileWriters;
    private final String message;

    private CmdParserV2(@NonNull SmfSendStrategy smfSendStrategy, @NonNull SimpleMessageFormat simpleMessageFormat, @NonNull FileWritersV2 fileWriters, @NonNull String message) {
        this.smfSendStrategy = smfSendStrategy;
        this.simpleMessageFormat = simpleMessageFormat;
        this.fileWriters = fileWriters;
        this.message = message;
    }

    public static CmdParserV2 parse(@NonNull String sCmd, @NonNull SmfSender smfSender, @NonNull TimeAndIpNamedFileWriterCreatorV2 fileWritersCreator) {
        SmfSendStrategy smfSendStrategy = null;
        SimpleMessageFormat simpleMessageFormat = null;
        List<FileWriterV2> fileWriterV2s = null;

        Deque<String> queue = new ArrayDeque<>(List.of(sCmd.split(" ")));

        Cmd cmd = Cmd.from(queue.poll()).orElseThrow(()-> new RuntimeException("일치하는 cmd 가 존재 하지 않습니다."));
        Notice notice = null;
        if(cmd == Cmd.NOTICE){
             notice = Notice.from(queue.poll()).orElseThrow(()-> new RuntimeException("일치하는 notice 가 존재하지 않습니다."));
        }
        String[] sAddresses = queue.poll().split(",");
        String message = String.join(" ", queue.poll());

        String owner = createFileWriterOwner(notice, cmd);
        simpleMessageFormat = createSimpleMessageFormat(message, notice, cmd);

        if(isAllAddressContain(sAddresses)){
            smfSendStrategy = new SmfAllSendStrategy(smfSender);
            fileWriterV2s = decorateOwnerFileWriter(owner, fileWritersCreator.createAll(LocalDateTime.now()));

            return new CmdParserV2(smfSendStrategy, simpleMessageFormat, new FileWritersV2(fileWriterV2s), message);
        }

        List<Address> addresses = createAddresses(sAddresses);

        smfSendStrategy = new SmfIpSendStrategy(addresses, smfSender);
        fileWriterV2s = decorateOwnerFileWriter(owner, fileWritersCreator.create(LocalDateTime.now(), addresses));

        return new CmdParserV2(smfSendStrategy, simpleMessageFormat, new FileWritersV2(fileWriterV2s), message);
    }

    private static <T extends FileWriterV2> List<FileWriterV2> decorateOwnerFileWriter(String owner, List<T> fileWriters){
        return fileWriters.stream()
            .map(fileWriter -> new FileOwnerWriterV2(owner, fileWriter))
            .collect(Collectors.toUnmodifiableList());
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
