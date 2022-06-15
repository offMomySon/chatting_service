package server.v4;

import common.MessageOwner;
import common.command.Cmd;
import common.command.Notice;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import server.Address;
import server.v4.message.file.FileMessageV4;
import server.v4.message.smf.SimpleMessageFormatV4;
import server.v4.message.smf.generic.GenericSimpleMessageFormatV4;
import server.v4.message.smf.notice.NoticeInfoSimpleMessageFormatV4;
import server.v4.message.smf.notice.NoticeWarnSimpleMessageFormatV4;
import server.v4.strategy.file.FileAllWriteStrategy;
import server.v4.strategy.file.FileIpWriteStrategy;
import server.v4.strategy.file.FileWriteStrategy;
import server.v4.strategy.smf.SmfAllWriteStrategy;
import server.v4.strategy.smf.SmfIpWriteStrategy;
import server.v4.strategy.smf.SmfWriteStrategy;
import static common.command.Cmd.SEND;

@Getter
public class CmdParserV4 {
    private static final Set<String> ALL_ADDRESS = Set.of("*", "ALL");
    private static final String CMD_DELIMITER = " ";
    private static final String ADDRESS_DELIMITER = ",";

    private final SmfWriteStrategy smfSendStrategy;
    private final FileWriteStrategy fileWriteStrategy;
    private final SimpleMessageFormatV4 simpleMessageFormatV4;
    private final FileMessageV4 fileMessage;

    public CmdParserV4(@NonNull SmfWriteStrategy smfSendStrategy,@NonNull FileWriteStrategy fileWriteStrategy, @NonNull SimpleMessageFormatV4 simpleMessageFormatV4, @NonNull FileMessageV4 fileMessage) {
        this.smfSendStrategy = smfSendStrategy;
        this.fileWriteStrategy = fileWriteStrategy;
        this.simpleMessageFormatV4 = simpleMessageFormatV4;
        this.fileMessage = fileMessage;
    }

    public static CmdParserV4 parse(@NonNull String sCmd, @NonNull MessageWriter messageWriter){
        SmfWriteStrategy smfWriteStrategy = null;
        FileWriteStrategy fileWriteStrategy = null;
        SimpleMessageFormatV4 simpleMessageFormatV4 = null;
        FileMessageV4 fileMessage = null;

        Deque<String> deque = new ArrayDeque<>(List.of(sCmd.split(" ")));

        Cmd cmd = Cmd.from(deque.poll()).orElseThrow(()-> new RuntimeException("일치하는 cmd 가 존재하지 않습니다."));
        Notice notice = null;
        if(cmd == Cmd.NOTICE){
            notice = Notice.from(deque.poll()).orElseThrow(()-> new RuntimeException("일치하는 notice 가 존재하지 않습니다."));
        }
        String[] sAddresses = deque.poll().split(ADDRESS_DELIMITER);
        String message = String.join(" ", deque);

        SimpleMessageFormatMessageOwnerCreator simpleMessageFormatMessageOwnerCreator = SimpleMessageFormatMessageOwnerCreator.create(cmd, notice, message);

        MessageOwner messageOwner = simpleMessageFormatMessageOwnerCreator.getMessageOwner();

        simpleMessageFormatV4 = simpleMessageFormatMessageOwnerCreator.getSimpleMessageFormatV4();
        fileMessage = new FileMessageV4(LocalDateTime.now(), messageOwner, message);

        if(isAllAddressContain(sAddresses)){
            smfWriteStrategy = new SmfAllWriteStrategy(messageWriter);
            fileWriteStrategy = new FileAllWriteStrategy(messageWriter);

            return new CmdParserV4(smfWriteStrategy, fileWriteStrategy, simpleMessageFormatV4, fileMessage);
        }

        List<Address> addresses = Arrays.stream(sAddresses).map(Address::new).collect(Collectors.toUnmodifiableList());
        smfWriteStrategy = new SmfIpWriteStrategy(messageWriter, addresses);
        fileWriteStrategy = new FileIpWriteStrategy(messageWriter, addresses);

        return new CmdParserV4(smfWriteStrategy, fileWriteStrategy, simpleMessageFormatV4, fileMessage);
    }

    private static boolean isAllAddressContain(String[] sAddresses) {
        return Arrays.stream(sAddresses)
            .map(StringUtils::upperCase)
            .anyMatch(ALL_ADDRESS::contains);
    }

    @Getter
    private static class SimpleMessageFormatMessageOwnerCreator {
        private final SimpleMessageFormatV4 simpleMessageFormatV4;
        private final MessageOwner messageOwner;

        public SimpleMessageFormatMessageOwnerCreator(@NonNull SimpleMessageFormatV4 simpleMessageFormatV4, @NonNull MessageOwner messageOwner) {
            this.simpleMessageFormatV4 = simpleMessageFormatV4;
            this.messageOwner = messageOwner;
        }

        public static SimpleMessageFormatMessageOwnerCreator create(@NonNull Cmd cmd, Notice notice, @NonNull String message){
            if(cmd == SEND){
                return new SimpleMessageFormatMessageOwnerCreator(new GenericSimpleMessageFormatV4(message), MessageOwner.SERVER);
            }

            if(Objects.isNull(notice)){
                throw new RuntimeException("notice is null");
            }

            switch (notice){
                case INFO:
                    return new SimpleMessageFormatMessageOwnerCreator(new NoticeInfoSimpleMessageFormatV4(message), MessageOwner.INFO);
                case WARN:
                    return new SimpleMessageFormatMessageOwnerCreator(new NoticeWarnSimpleMessageFormatV4(message), MessageOwner.WARN);
            }
            throw new RuntimeException("일치하는 type 이 존재하지 않습니다.");
        }

    }
}
