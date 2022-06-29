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
    private final List<Destination> destinations;

    public MessageIpWriteStrategy(@NonNull MessageWriter messageWriter, @NonNull List<Destination> destinations) {
        this.messageWriter = messageWriter;
        this.destinations = destinations;
    }

    @Override
    public void write(@NonNull Message message) {
        destinations
            .forEach(destination -> messageWriter.write(destination, message));
    }
}
