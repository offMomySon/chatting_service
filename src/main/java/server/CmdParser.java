package server;

import common.command.Cmd;
import common.command.NoticePrefix;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import server.message.file.LogInfoMessage;
import server.message.file.LogServerMessage;
import server.message.file.LogWarnMessage;
import server.message.smf.generic.GenericSimpleMessage;
import server.message.smf.notice.NoticeInfoSimpleMessage;
import server.message.smf.notice.NoticeWarnSimpleMessage;
import server.message.Message;
import server.writer.MessageWriter;
import server.writer.MessageAllWriteStrategy;
import server.writer.MessageDestinationWriteStrategy;
import server.writer.MessageWriteStrategy;
import static server.writer.Usage.FILE;
import static server.writer.Usage.SOCKET;

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
        NoticePrefix noticePrefix = null;
        if(Cmd.NOTICE == cmd){
            noticePrefix = NoticePrefix.from(cmdQueue.poll()).orElseThrow(()-> new RuntimeException("not exist notice"));
        }
        String[] sAddresses = cmdQueue.poll().split(ADDRESS_DELIMITER);
        String message = cmdQueue.poll();

        StrategyCreator strategyCreator = StrategyCreator.create(sAddresses, messageWriter);
        MessageCreator messageCreator = MessageCreator.create(cmd, noticePrefix, message);

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

        public static MessageCreator create(@NonNull Cmd cmd, NoticePrefix noticePrefix, @NonNull String message){
            if(cmd == Cmd.SEND){
                return new MessageCreator(LogServerMessage.ofCurrent(message), new GenericSimpleMessage(message));
            }

            if(noticePrefix == NoticePrefix.INFO) {
                return new MessageCreator(LogInfoMessage.ofCurrent(message), new NoticeInfoSimpleMessage(message));
            }
            if(noticePrefix == NoticePrefix.WARN) {
                return new MessageCreator(LogWarnMessage.ofCurrent(message), new NoticeWarnSimpleMessage(message));
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
