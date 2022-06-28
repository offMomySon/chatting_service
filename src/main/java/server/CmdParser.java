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
import server.message.smf.SimpleMessageFormat;
import server.message.smf.generic.GenericSimpleMessageFormat;
import server.message.smf.notice.NoticeInfoSimpleMessageFormat;
import server.message.smf.notice.NoticeWarnSimpleMessageFormat;
import server.v5.MessageWriter;
import server.writer.file.FileAllWriteStrategy;
import server.writer.file.FileIpWriteStrategy;
import server.writer.file.FileWriteStrategy;
import server.writer.smf.SmfAllSendStrategy;
import server.writer.smf.SmfIpSendStrategy;
import server.writer.smf.SmfSendStrategy;

@Getter
public class CmdParser {
    private static final Set<String> ALL_ADDRESS = Set.of("*", "ALL");
    private static final String CMD_DELIMITER = " ";
    private static final String ADDRESS_DELIMITER = ",";


    private final FileWriteStrategy fileWriteStrategy;
    private final SmfSendStrategy smfSendStrategy;
    private final FileMessage fileMessage;
    private final SimpleMessageFormat smfMessage;

    private CmdParser(@NonNull FileWriteStrategy fileWriteStrategy, @NonNull SmfSendStrategy smfSendStrategy, @NonNull FileMessage fileMessage, @NonNull SimpleMessageFormat smfMessage) {
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

        return new CmdParser(strategyCreator.getFileWriteStrategy(),
                             strategyCreator.getSmfSendStrategy(),
                             messageCreator.getFileMessage(),
                             messageCreator.getSimpleMessageFormat());
    }

    @Getter
    private static class MessageCreator{
        private final FileMessage fileMessage;
        private final SimpleMessageFormat simpleMessageFormat;

        private MessageCreator(@NonNull FileMessage fileMessage, @NonNull SimpleMessageFormat simpleMessageFormat) {
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
        private final FileWriteStrategy fileWriteStrategy;
        private final SmfSendStrategy smfSendStrategy;

        private StrategyCreator(@NonNull FileWriteStrategy fileWriteStrategy, @NonNull SmfSendStrategy smfSendStrategy) {
            this.fileWriteStrategy = fileWriteStrategy;
            this.smfSendStrategy = smfSendStrategy;
        }

        public static StrategyCreator create(@NonNull String[] sAddresses, @NonNull MessageWriter messageWriter){
            if(isContainAllAddress(sAddresses)){
                return new StrategyCreator(new FileAllWriteStrategy(messageWriter), new SmfAllSendStrategy(messageWriter));
            }

            List<Address> addresses = Arrays.stream(sAddresses).map(Address::new).collect(Collectors.toUnmodifiableList());

            return new StrategyCreator(FileIpWriteStrategy.from(addresses, messageWriter), SmfIpSendStrategy.from(addresses, messageWriter));
        }

        private static boolean isContainAllAddress(String[] addresses){
            return Arrays.stream(addresses)
                .map(StringUtils::upperCase)
                .anyMatch(ALL_ADDRESS::contains);
        }
    }
}
