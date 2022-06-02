package server.sender;

import common.protocal.SimpleMessageFormat;
import common.repository.AddressRepository;
import java.util.List;
import lombok.NonNull;
import server.destination.Address;

public class MultiIpSmfSmfSender implements SmfSender {
    private final AddressRepository addressRepository;
    private final List<Address> addresses;
    private final SimpleMessageFormat simpleMessageFormat;

    public MultiIpSmfSmfSender(@NonNull AddressRepository addressRepository, @NonNull List<Address> addresses,
                               @NonNull SimpleMessageFormat simpleMessageFormat) {
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
