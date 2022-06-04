package server.writer.smf;

import lombok.NonNull;
import server.message.SimpleMessageFormat;

public interface SmfSendStrategy {
    void send(@NonNull SimpleMessageFormat simpleMessageFormat);
}
