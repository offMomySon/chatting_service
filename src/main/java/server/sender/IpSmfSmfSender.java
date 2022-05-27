package server.sender;

import common.repository.AddressRepository;
import common.view.format.SimpleMessageFormat;
import java.io.IOException;
import java.io.ObjectOutputStream;
import lombok.NonNull;
import server.destination.address.Address;

public class IpSmfSmfSender implements SmfSender {
    private final AddressRepository addressRepository;
    private final Address address;
    private final SimpleMessageFormat simpleMessageFormat;
    public IpSmfSmfSender(@NonNull AddressRepository addressRepository, @NonNull Address address,
                          SimpleMessageFormat simpleMessageFormat) {
        this.addressRepository = addressRepository;
        this.address = address;
        this.simpleMessageFormat = simpleMessageFormat;
    }

    @Override
    public void send() {
        ObjectOutputStream objectOutputStream = addressRepository.get(address);

        try {
            objectOutputStream.writeObject(simpleMessageFormat);
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
