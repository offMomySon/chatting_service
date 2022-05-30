package server.sender;

import common.repository.AddressRepository;
import java.io.IOException;
import lombok.NonNull;
import server.protocal.SimpleMessageFormat;

public class AllSmfSmfSender implements SmfSender {
    private final AddressRepository addressRepository;
    private final server.protocal.SimpleMessageFormat simpleMessageFormat;
    public AllSmfSmfSender(@NonNull AddressRepository addressRepository, SimpleMessageFormat simpleMessageFormat) {
        this.addressRepository = addressRepository;
        this.simpleMessageFormat = simpleMessageFormat;
    }

    @Override
    public void send() {
        addressRepository.values().stream()
            .forEach(out -> {
                try {
                    out.writeObject(simpleMessageFormat);
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
