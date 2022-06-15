package server.v4.strategy.smf;

import lombok.NonNull;
import server.v4.MessageWriter;
import server.v4.message.smf.SimpleMessageFormatV4;

public class SmfAllWriteStrategy implements SmfWriteStrategy{
    private final MessageWriter messageWriter;

    public SmfAllWriteStrategy(@NonNull MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    @Override
    public void write(@NonNull SimpleMessageFormatV4 simpleMessageFormatV4) {
        messageWriter.writeAll(simpleMessageFormatV4);
    }
}
