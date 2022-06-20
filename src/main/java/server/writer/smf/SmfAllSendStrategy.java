package server.writer.smf;

import lombok.NonNull;
import server.message.smf.SimpleMessageFormat;
import server.v5.MessageWriter;
import static server.v5.Usage.SOCKET;

public class SmfAllSendStrategy implements SmfSendStrategy{
    private final MessageWriter messageWriter;

    public SmfAllSendStrategy(@NonNull MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    @Override
    public void send(@NonNull SimpleMessageFormat message) {
        messageWriter.writeAll(SOCKET, message);
    }
}
