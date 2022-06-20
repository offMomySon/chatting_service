package server.writer.file;

import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.Address;
import server.message.file.FileMessage;
import server.v5.AddressDirection;
import server.v5.MessageWriter;
import server.writer.smf.SmfIpSendStrategy;
import static server.v5.Usage.FILE;

public class FileIpWriteStrategy implements FileWriteStrategy{
    private final List<AddressDirection> addresses;
    private final MessageWriter messageWriter;

    public FileIpWriteStrategy(@NonNull List<AddressDirection> addresses, MessageWriter messageWriter) {
        this.addresses = addresses;
        this.messageWriter = messageWriter;
    }
    public static SmfIpSendStrategy from(@NonNull List<Address> addresses, @NonNull MessageWriter messageWriter){
        List<AddressDirection> addressDirections = addresses.stream().map(address -> new AddressDirection(address, FILE)).collect(Collectors.toUnmodifiableList());

        return new SmfIpSendStrategy(addressDirections, messageWriter);
    }

    @Override
    public void write(FileMessage message) {
        addresses.forEach(addressDirection -> messageWriter.write(addressDirection, message));
    }
}
