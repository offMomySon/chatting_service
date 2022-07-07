package server;

import common.MessageOwner;
import common.command.Cmd;
import common.command.Notice;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import server.message.file.FileMessage;
import server.message.smf.generic.GenericSimpleMessageFormat;
import server.message.smf.notice.NoticeInfoSimpleMessageFormat;
import server.message.smf.notice.NoticeWarnSimpleMessageFormat;
import server.v5.Message;
import server.v5.MessageWriter;
import server.writer.MessageAllWriteStrategy;
import server.writer.MessageDestinationWriteStrategy;
import server.writer.MessageWriteStrategy;
import static server.v5.Usage.FILE;
import static server.v5.Usage.SOCKET;

@Getter
public class CmdParser {
    private static final Set<String> ALL_ADDRESS = Set.of("*", "ALL");
    private static final String CMD_DELIMITER = " ";
    private static final String ADDRESS_DELIMITER = ",";

    private final MessageWriteStrategy fileWriteStrategy;
    private final MessageWriteStrategy smfSendStrategy;
    private final Message fileMessage;
    private final Message smfMessage;

    public CmdParser(@NonNull MessageWriteStrategy fileWriteStrategy, @NonNull MessageWriteStrategy smfSendStrategy, @NonNull Message fileMessage, @NonNull Message smfMessage) {
        this.fileWriteStrategy = fileWriteStrategy;
        this.smfSendStrategy = smfSendStrategy;
        this.fileMessage = fileMessage;
        this.smfMessage = smfMessage;
    }

    public static CmdParser parse(@NonNull String sCmd, @NonNull MessageWriter messageWriter){
        Queue<String> cmdQueue = new ArrayDeque<>(List.of(sCmd.split(CMD_DELIMITER)));

        Cmd cmd = Cmd.from(cmdQueue.poll()).orElseThrow(() -> new RuntimeException("not exist cmd"));
        Notice notice = null;
        if(Cmd.NOTICE == cmd){
            notice = Notice.from(cmdQueue.poll()).orElseThrow(()-> new RuntimeException("not exist notice"));
        }
        String[] sAddresses = cmdQueue.poll().split(ADDRESS_DELIMITER);
        String message = cmdQueue.poll();

        StrategyCreator strategyCreator = StrategyCreator.create(sAddresses, messageWriter);
        MessageCreator messageCreator = MessageCreator.create(cmd, notice, message);

        return new CmdParser(strategyCreator.getFileMessageWriteStrategy(),
                             strategyCreator.getSmfMessageWriteStrategy(),
                             messageCreator.getFileMessage(),
                             messageCreator.getSimpleMessageFormat());
    }

    @Getter
    private static class MessageCreator{
        private final Message fileMessage;
        private final Message simpleMessageFormat;

        private MessageCreator(@NonNull Message fileMessage, @NonNull Message simpleMessageFormat) {
            this.fileMessage = fileMessage;
            this.simpleMessageFormat = simpleMessageFormat;
        }

        public static MessageCreator create(@NonNull Cmd cmd, Notice notice, @NonNull String message){
            if(cmd == Cmd.SEND){
                return new MessageCreator(new FileMessage(LocalDateTime.now(), MessageOwner.SERVER, message),
                                          new GenericSimpleMessageFormat(message));
            }

            if(notice == Notice.INFO) {
                return new MessageCreator(new FileMessage(LocalDateTime.now(), MessageOwner.INFO, message),
                                          new NoticeInfoSimpleMessageFormat(message));
            }
            if(notice == Notice.WARN) {
                return new MessageCreator(new FileMessage(LocalDateTime.now(), MessageOwner.WARN, message),
                                          new NoticeWarnSimpleMessageFormat(message));
            }

            throw new RuntimeException("not exist cmd");
        }
    }

    @Getter
    private static class StrategyCreator{
        private final MessageWriteStrategy fileMessageWriteStrategy;
        private final MessageWriteStrategy smfMessageWriteStrategy;

        public StrategyCreator(@NonNull MessageWriteStrategy fileMessageWriteStrategy, @NonNull MessageWriteStrategy smfMessageWriteStrategy) {
            this.fileMessageWriteStrategy = fileMessageWriteStrategy;
            this.smfMessageWriteStrategy = smfMessageWriteStrategy;
        }

        public static StrategyCreator create(@NonNull String[] sAddresses, @NonNull MessageWriter messageWriter){
            if(isContainAllAddress(sAddresses)){
                return new StrategyCreator(new MessageAllWriteStrategy(messageWriter, FILE), new MessageAllWriteStrategy(messageWriter, SOCKET));
            }

            List<Address> addresses = Arrays.stream(sAddresses).map(Address::new).collect(Collectors.toUnmodifiableList());

            return new StrategyCreator(MessageDestinationWriteStrategy.from(messageWriter, addresses, FILE), MessageDestinationWriteStrategy.from(messageWriter, addresses, SOCKET));
        }

        private static boolean isContainAllAddress(String[] addresses){
            return Arrays.stream(addresses)
                .map(StringUtils::upperCase)
                .anyMatch(ALL_ADDRESS::contains);
        }
    }
}
