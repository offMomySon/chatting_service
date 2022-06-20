package server.writer.smf;

import lombok.NonNull;
import server.message.smf.SimpleMessageFormat;

public interface SmfSendStrategy {
    void send(@NonNull SimpleMessageFormat simpleMessageFormat);
}
