package server.sender.v2;

import java.util.List;
import lombok.NonNull;
import server.destination.Address;

public class FileIpWriteStrategy implements FileWriteStrategy{
    private final List<Address> addresses;
    private final FileWriter fileWriter;
    private final String owner;

    public FileIpWriteStrategy(@NonNull List<Address> addresses, @NonNull FileWriter fileWriter, @NonNull String owner) {
        this.addresses = addresses;
        this.fileWriter = fileWriter;
        this.owner = owner;
    }

    @Override
    public void write(String message) {
        fileWriter.write(message, owner, addresses);
    }
}
