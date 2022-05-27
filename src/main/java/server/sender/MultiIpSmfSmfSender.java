package server.sender;

import common.repository.AddressRepository;
import common.view.format.SimpleMessageFormat;
import java.util.List;
import lombok.NonNull;
import server.destination.address.Address;

public class MultiIpSmfSmfSender implements SmfSender {
    private final AddressRepository addressRepository;
    private final List<Address> addresses;
    private final SimpleMessageFormat simpleMessageFormat;

    public MultiIpSmfSmfSender(@NonNull AddressRepository addressRepository, @NonNull List<Address> addresses,
                               SimpleMessageFormat simpleMessageFormat) {
        this.addressRepository = addressRepository;
        this.addresses = addresses;
        this.simpleMessageFormat = simpleMessageFormat;
    }

    @Override
    public void send() {
        addresses
            .forEach(address -> new IpSmfSmfSender(addressRepository, address, simpleMessageFormat).send());
    }
}