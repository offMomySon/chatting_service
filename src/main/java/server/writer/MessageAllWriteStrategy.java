package server.writer;

import lombok.NonNull;
import server.v5.Message;
import server.v5.MessageWriter;
import server.v5.Usage;

public class MessageAllWriteStrategy implements MessageWriteStrategy{
    private final MessageWriter messageWriter;


    public MessageAllWriteStrategy(@NonNull MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    @Override
    public void write(@NonNull Usage usage, @NonNull Message message) {
        messageWriter.writeAll(usage, message);
    }
}
