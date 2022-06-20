package server.writer.smf;

import lombok.NonNull;
import server.message.smf.SimpleMessageFormat;
import server.v5.MessageWriter;
import static server.v5.Usage.SOCKET;

public class SmfAllSendStrategy implements SmfSendStrategy{
    private final MessageWriter messageWriter;
    private final SimpleMessageFormat message;

    public SmfAllSendStrategy(@NonNull MessageWriter messageWriter, @NonNull SimpleMessageFormat message) {
        this.messageWriter = messageWriter;
        this.message = message;
    }

    @Override
    public void send() {
        messageWriter.writeAll(SOCKET, message);
    }
}
