package server.sender.v2;

import java.util.List;
import lombok.NonNull;
import server.destination.Address;
import server.protocal.SimpleMessageFormat;

public class SmfIpSendStrategy implements SmfSendStrategy{
    private final List<Address> addresses;
    private final SmfSender smfSender;

    public SmfIpSendStrategy(@NonNull List<Address> addresses, @NonNull SmfSender smfSender) {
        this.addresses = addresses;
        this.smfSender = smfSender;
    }

    @Override
    public void send(SimpleMessageFormat smf) {
        smfSender.send(smf, addresses);
    }
}
