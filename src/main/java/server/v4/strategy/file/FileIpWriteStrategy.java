package server.v4.strategy.file;

import java.util.List;
import lombok.NonNull;
import server.Address;
import server.v4.MessageWriter;
import server.v4.message.file.FileMessageV4;

public class FileIpWriteStrategy implements FileWriteStrategy{
    private final MessageWriter messageWriter;
    private final List<Address> addresses;

    public FileIpWriteStrategy(@NonNull MessageWriter messageWriter, @NonNull List<Address> addresses) {
        this.messageWriter = messageWriter;
        this.addresses = addresses;
    }
    @Override
    public void write(@NonNull FileMessageV4 fileMessage) {
        messageWriter.write(fileMessage, addresses);
    }
}
