package server.writer;

import lombok.NonNull;
import server.v5.Message;
import server.v5.MessageWriter;
import server.v5.Usage;

/**
 * 역할.
 * usage 에 해당하는
 * 모든 destination 에 대해 message 를 쓰는 역할.
 */
public class MessageAllWriteStrategy implements MessageWriteStrategy{
    private final MessageWriter messageWriter;

    private final Usage usage;


    public MessageAllWriteStrategy(@NonNull MessageWriter messageWriter, @NonNull Usage usage) {
        this.messageWriter = messageWriter;
        this.usage = usage;
    }

    @Override
    public void write(@NonNull Message message) {
        messageWriter.writeAll(message, usage);
    }
}
