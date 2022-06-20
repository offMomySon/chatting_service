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
    private final List<Destination> addresses;
    private final MessageWriter messageWriter;

    private FileIpWriteStrategy(@NonNull List<Destination> addresses, MessageWriter messageWriter) {
        this.addresses = addresses;
        this.messageWriter = messageWriter;
    }
    public static FileIpWriteStrategy from(@NonNull List<Address> addresses, @NonNull MessageWriter messageWriter){
        List<Destination> destinations = addresses.stream().map(address -> new Destination(address, FILE)).collect(Collectors.toUnmodifiableList());

        return new FileIpWriteStrategy(destinations, messageWriter);
    }

    @Override
    public void write(@NonNull FileMessage message) {
        addresses.forEach(addressDirection -> messageWriter.write(addressDirection, message));
    }
}
