package server.sender;

import common.protocal.SimpleMessageFormat;
import common.repository.AddressRepository;
import java.io.BufferedWriter;
import java.io.IOException;
import lombok.NonNull;
import server.destination.Address;

public class IpSmfSmfSender implements SmfSender {
    private final AddressRepository addressRepository;
    private final Address address;
    private final SimpleMessageFormat simpleMessageFormat;

    public IpSmfSmfSender(@NonNull AddressRepository addressRepository, @NonNull Address address,
                          @NonNull SimpleMessageFormat simpleMessageFormat) {
        this.addressRepository = addressRepository;
        this.address = address;
        this.simpleMessageFormat = simpleMessageFormat;
    }

    @Override
    public void send() {
        BufferedWriter out = addressRepository.get(address);

        try {
            out.write(simpleMessageFormat.createMessage());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
