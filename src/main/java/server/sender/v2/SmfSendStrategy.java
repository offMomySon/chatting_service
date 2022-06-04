package server.sender.v2;

import lombok.NonNull;
import server.protocal.SimpleMessageFormat;

public interface SmfSendStrategy {
    void send(@NonNull SimpleMessageFormat simpleMessageFormat);
}
