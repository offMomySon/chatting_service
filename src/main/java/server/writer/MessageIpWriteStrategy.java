package server.writer;

import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.Address;
import server.v5.Destination;
import server.v5.Message;
import server.v5.MessageWriter;
import server.v5.Usage;

public class MessageIpWriteStrategy implements MessageWriteStrategy{
    private final MessageWriter messageWriter;
    private final List<Address> addresses;

    public MessageIpWriteStrategy(@NonNull MessageWriter messageWriter, @NonNull List<Address> addresses) {
        this.messageWriter = messageWriter;
        this.addresses = addresses;
    }

    @Override
    public void write(@NonNull Usage usage, @NonNull Message message) {
        List<Destination> destinations = addresses.stream()
            .map(address -> new Destination(address, usage))
            .collect(Collectors.toUnmodifiableList());

        destinations.forEach(destination -> messageWriter.write(destination, message));
    }
}
