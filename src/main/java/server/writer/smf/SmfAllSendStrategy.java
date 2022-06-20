package server.writer.smf;

import lombok.NonNull;
import server.message.smf.SimpleMessageFormat;
import server.v5.MessageWriter;

public class SmfAllSendStrategy implements SmfSendStrategy{
    private final MessageWriter messageWriter;

    public SmfAllSendStrategy(@NonNull MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    @Override
    public void send(SimpleMessageFormat message) {
        messageWriter.writeAll(message);
    }
}
