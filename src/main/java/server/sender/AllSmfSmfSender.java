package server.sender;

import common.protocal.SimpleMessageFormat;
import common.repository.AddressRepository;
import java.io.IOException;
import lombok.NonNull;

public class AllSmfSmfSender implements SmfSender {
    private final AddressRepository addressRepository;
    private final SimpleMessageFormat simpleMessageFormat;

    public AllSmfSmfSender(@NonNull AddressRepository addressRepository,
                           @NonNull SimpleMessageFormat simpleMessageFormat) {
        this.addressRepository = addressRepository;
        this.simpleMessageFormat = simpleMessageFormat;
    }

    @Override
    public void send() {
        addressRepository.values().stream()
            .forEach(out -> {
                try {
                    out.write(simpleMessageFormat.createMessage());
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
