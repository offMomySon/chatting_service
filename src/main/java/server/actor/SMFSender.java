package server.actor;

import common.view.format.SimpleMessageFormat;
import common.repository.AddressRepository;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.destination.address.Address;

public class SMFSender {
    private static final List<String> ALL_ADDRESS = List.of("*", "all");
    private static final String ADDRESS_DELIMITER = ",";

    private final SimpleMessageFormat simpleMessageFormat;
    private final AddressRepository addressRepository;

    public SMFSender(@NonNull SimpleMessageFormat simpleMessageFormat, @NonNull AddressRepository addressRepository) {
        this.simpleMessageFormat = simpleMessageFormat;
        this.addressRepository = addressRepository;
    }

    public void send(String address) throws IOException {
        if(ALL_ADDRESS.contains(address)){
            sendAll();
        }

        List<Address> addresses = Arrays.stream(address.split(ADDRESS_DELIMITER))
            .map(Address::new)
            .collect(Collectors.toList());

        sendSpecificAddress(addresses);
    }

    private void sendAll(){
        Collection<ObjectOutputStream> values = addressRepository.values();

        values.forEach( out -> {
                try {
                    out.writeObject(simpleMessageFormat);
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException("Fail to send object.", e);
                }
        });
    }

    private void sendSpecificAddress(List<Address> addresses){
        addresses.stream()
            .filter(addressRepository::containsKey)
            .map(addressRepository::get)
            .forEach(out -> {
                try {
                    out.writeObject(simpleMessageFormat);
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException("Fail to send object.", e);
                }
            });
    }
}
