package server.writer.smf;

import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.Address;
import server.message.smf.SimpleMessageFormat;
import server.v5.Destination;
import server.v5.MessageWriter;
import static server.v5.Usage.SOCKET;

public class SmfIpSendStrategy implements SmfSendStrategy{
    private final List<Destination> addresses;
    private final MessageWriter messageWriter;

    public SmfIpSendStrategy(@NonNull List<Destination> addresses, @NonNull MessageWriter messageWriter) {
        this.addresses = addresses;
        this.messageWriter = messageWriter;
    }

    public static SmfIpSendStrategy from(@NonNull List<Address> addresses,  @NonNull MessageWriter messageWriter){
        List<Destination> destinations = addresses.stream().map(address -> new Destination(address, SOCKET)).collect(Collectors.toUnmodifiableList());

        return new SmfIpSendStrategy(destinations, messageWriter);
    }

    @Override
    public void send(@NonNull SimpleMessageFormat message) {
        addresses.forEach(addressDirection -> messageWriter.write(addressDirection, message));
    }
}
