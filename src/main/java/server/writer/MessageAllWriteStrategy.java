package server.writer;

import lombok.NonNull;
import server.v5.Message;
import server.v5.MessageWriter;
import server.v5.Usage;

/**
 * 역할.
 * 모든 address 에 대해 message 를 쓰는 역할.
 */
public class MessageAllWriteStrategy implements MessageWriteStrategy{
    private final MessageWriter messageWriter;


    public MessageAllWriteStrategy(@NonNull MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    @Override
    public void write(@NonNull Message message, @NonNull Usage usage) {
        messageWriter.writeAll(message, usage);
    }
}
