package server.v3;

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
import server.message.file.FileMessage;
import server.message.smf.SimpleMessageFormat;
import server.message.smf.generic.GenericSimpleMessageFormat;
import server.message.smf.notice.NoticeInfoSimpleMessageFormat;
import server.message.smf.notice.NoticeWarnSimpleMessageFormat;
import server.writer.smf.SmfAllSendStrategy;
import server.writer.smf.SmfIpSendStrategy;
import server.writer.smf.SmfSendStrategy;
import server.writer.smf.SmfSender;
import static common.command.Cmd.SEND;

@Getter
public class CmdParserV3 {
    private static final Set<String> ALL_ADDRESS = Set.of("*", "ALL");
    private static final String CMD_DELIMITER = " ";
    private static final String ADDRESS_DELIMITER = ",";

    private final SmfSendStrategy smfSendStrategy;
    private final SimpleMessageFormat simpleMessageFormat;
    private final FileWritersV3 fileWritersV3;
    private final FileMessage fileMessage;

    private CmdParserV3(@NonNull SmfSendStrategy smfSendStrategy, @NonNull SimpleMessageFormat simpleMessageFormat,
                       @NonNull FileWritersV3 fileWritersV3, @NonNull FileMessage fileMessage) {
        this.smfSendStrategy = smfSendStrategy;
        this.simpleMessageFormat = simpleMessageFormat;
        this.fileWritersV3 = fileWritersV3;
        this.fileMessage = fileMessage;
    }

    public static CmdParserV3 parse(@NonNull String sCmd, @NonNull SmfSender smfSender, @NonNull AddressRepository addressRepository){
        SmfSendStrategy smfSendStrategy = null;
        SimpleMessageFormat simpleMessageFormat = null;
        FileWritersV3 fileWritersV3 = null;
        MessageOwner messageOwner = null;

        Deque<String> deque = new ArrayDeque<>(List.of(sCmd.split(" ")));

        Cmd cmd = Cmd.from(deque.poll()).orElseThrow(()-> new RuntimeException("일치하는 cmd 가 존재하지 않습니다."));
        Notice notice = null;
        if(cmd == Cmd.NOTICE){
            notice = Notice.from(deque.poll()).orElseThrow(()-> new RuntimeException("일치하는 notice 가 존재하지 않습니다."));
        }
        String[] sAddresses = deque.poll().split(ADDRESS_DELIMITER);
        String message = String.join(" ", deque);

        SimpleMessageFormatMessageOwnerCreator simpleMessageFormatMessageOwnerCreator = SimpleMessageFormatMessageOwnerCreator.create(cmd, notice, message);
        messageOwner = simpleMessageFormatMessageOwnerCreator.getMessageOwner();
        simpleMessageFormat = simpleMessageFormatMessageOwnerCreator.getSimpleMessageFormat();

        FileMessage fileMessage  = new FileMessage(LocalDateTime.now(), messageOwner, message);

        if(isAllAddressContain(sAddresses)){
            List<Address> allAddress = addressRepository.findAll();
            List<TimeAddressNamedFileBuffedWriter> fileBuffedWriters = allAddress.stream().map(address -> TimeAddressNamedFileBuffedWriter.from(LocalDateTime.now(), address)).collect(Collectors.toUnmodifiableList());
            fileWritersV3 = FileWritersV3.from(fileBuffedWriters);

            smfSendStrategy = new SmfAllSendStrategy(smfSender);

            return new CmdParserV3(smfSendStrategy, simpleMessageFormat, fileWritersV3, fileMessage);
        }

        List<Address> requestAddress = Arrays.stream(sAddresses).map(Address::new).collect(Collectors.toUnmodifiableList());
        List<Address> targetAddresses = addressRepository.find(requestAddress);
        List<TimeAddressNamedFileBuffedWriter> fileBuffedWriters = targetAddresses.stream().map(address-> TimeAddressNamedFileBuffedWriter.from(LocalDateTime.now(), address)).collect(Collectors.toUnmodifiableList());
        fileWritersV3 = FileWritersV3.from(fileBuffedWriters);
        smfSendStrategy = new SmfIpSendStrategy(targetAddresses, smfSender);

        return new CmdParserV3(smfSendStrategy, simpleMessageFormat, fileWritersV3, fileMessage);
    }

    private static boolean isAllAddressContain(String[] sAddresses) {
        return Arrays.stream(sAddresses)
            .map(StringUtils::upperCase)
            .anyMatch(ALL_ADDRESS::contains);
    }

    @Getter
    private static class SimpleMessageFormatMessageOwnerCreator {
        private final SimpleMessageFormat simpleMessageFormat;
        private final MessageOwner messageOwner;

        private SimpleMessageFormatMessageOwnerCreator(@NonNull SimpleMessageFormat simpleMessageFormat,
                                                        @NonNull MessageOwner messageOwner) {
            this.simpleMessageFormat = simpleMessageFormat;
            this.messageOwner = messageOwner;
        }

        public static SimpleMessageFormatMessageOwnerCreator create(@NonNull Cmd cmd, Notice notice, @NonNull String message){
            if(cmd == SEND){
                return new SimpleMessageFormatMessageOwnerCreator(new GenericSimpleMessageFormat(message), MessageOwner.SERVER);
            }

            if(Objects.isNull(notice)){
                throw new RuntimeException("notice is null");
            }

            switch (notice){
                case INFO:
                    return new SimpleMessageFormatMessageOwnerCreator(new NoticeInfoSimpleMessageFormat(message), MessageOwner.INFO);
                case WARN:
                    return new SimpleMessageFormatMessageOwnerCreator(new NoticeWarnSimpleMessageFormat(message), MessageOwner.WARN);
            }
            throw new RuntimeException("일치하는 type 이 존재하지 않습니다.");
        }

    }
}
