package server.writer.smf;

import lombok.NonNull;
import server.message.smf.SimpleMessageFormat;

public class SmfAllSendStrategy implements SmfSendStrategy{
    private final SmfSender smfSender;

    public SmfAllSendStrategy(@NonNull SmfSender smfSender) {
        this.smfSender = smfSender;
    }

    @Override
    public void send(SimpleMessageFormat smf) {
        smfSender.sendAll(smf);
    }

}
