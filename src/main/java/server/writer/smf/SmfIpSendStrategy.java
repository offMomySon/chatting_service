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
    private final List<Destination> destinations;
    private final SimpleMessageFormat message;
    private final MessageWriter messageWriter;

    public SmfIpSendStrategy(@NonNull List<Destination> destinations, @NonNull SimpleMessageFormat message, @NonNull MessageWriter messageWriter) {
        this.destinations = destinations;
        this.message = message;
        this.messageWriter = messageWriter;
    }

    public static SmfIpSendStrategy from(@NonNull List<Address> addresses, @NonNull SimpleMessageFormat message, @NonNull MessageWriter messageWriter){
        List<Destination> destinations = addresses.stream()
            .map(address -> new Destination(address, SOCKET))
            .collect(Collectors.toUnmodifiableList());

        return new SmfIpSendStrategy(destinations, message, messageWriter);
    }

    @Override
    public void send() {
        destinations.forEach(addressDirection -> messageWriter.write(addressDirection, message));
    }
}
