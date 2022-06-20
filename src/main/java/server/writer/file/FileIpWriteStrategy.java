package server.writer.file;

import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.Address;
import server.message.file.FileMessage;
import server.v5.Destination;
import server.v5.MessageWriter;
import static server.v5.Usage.FILE;

public class FileIpWriteStrategy implements FileWriteStrategy{
    private final List<Destination> destinations;
    private final FileMessage message;
    private final MessageWriter messageWriter;

    public FileIpWriteStrategy(@NonNull List<Destination> destinations, @NonNull FileMessage message, @NonNull MessageWriter messageWriter) {
        this.destinations = destinations;
        this.message = message;
        this.messageWriter = messageWriter;
    }
    public static FileIpWriteStrategy from(@NonNull List<Address> addresses, @NonNull FileMessage message, @NonNull MessageWriter messageWriter){
        List<Destination> destinations = addresses.stream()
            .map(address -> new Destination(address, FILE))
            .collect(Collectors.toUnmodifiableList());

        return new FileIpWriteStrategy(destinations, message, messageWriter);
    }

    @Override
    public void write() {
        destinations.forEach(destination -> messageWriter.write(destination, message));
    }
}
