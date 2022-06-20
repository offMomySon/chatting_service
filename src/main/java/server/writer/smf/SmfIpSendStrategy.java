package server.writer.smf;

import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.Address;
import server.message.smf.SimpleMessageFormat;
import server.v5.AddressDirection;
import server.v5.MessageWriter;
import static server.v5.Usage.SOCKET;

public class SmfIpSendStrategy implements SmfSendStrategy{
    private final List<AddressDirection> addresses;
    private final MessageWriter messageWriter;

    public SmfIpSendStrategy(@NonNull List<AddressDirection> addresses, @NonNull MessageWriter messageWriter) {
        this.addresses = addresses;
        this.messageWriter = messageWriter;
    }

    public static SmfIpSendStrategy from(@NonNull List<Address> addresses,  @NonNull MessageWriter messageWriter){
        List<AddressDirection> addressDirections = addresses.stream().map(address -> new AddressDirection(address, SOCKET)).collect(Collectors.toUnmodifiableList());

        return new SmfIpSendStrategy(addressDirections, messageWriter);
    }

    @Override
    public void send(SimpleMessageFormat message) {
        addresses.forEach(addressDirection -> messageWriter.write(addressDirection, message));
    }
}
