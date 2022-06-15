package server.v4.strategy.smf;

import java.util.List;
import lombok.NonNull;
import server.Address;
import server.v4.MessageWriter;
import server.v4.message.smf.SimpleMessageFormatV4;

public class SmfIpWriteStrategy implements SmfWriteStrategy {
    private final MessageWriter messageWriter;
    private final List<Address> addresses;

    public SmfIpWriteStrategy(@NonNull MessageWriter messageWriter, @NonNull List<Address> addresses) {
        this.messageWriter = messageWriter;
        this.addresses = addresses;
    }

    @Override
    public void write(@NonNull SimpleMessageFormatV4 message) {
        messageWriter.write(message, addresses);
    }
}
