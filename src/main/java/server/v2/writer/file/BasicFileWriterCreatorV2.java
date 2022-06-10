package server.v2.writer.file;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import server.Address;
import static java.util.stream.Collectors.toUnmodifiableList;

public class BasicFileWriterCreatorV2 {
    private final Set<Address> addresses = new HashSet<>();

    public void addAddress(@NonNull Address address){
        addresses.add(address);
    }

    public List<BasicFileWriterV2> create(LocalDateTime time, Collection<Address> requestAddress){
        List<Address> destinations = requestAddress.stream().filter(addresses::contains).collect(toUnmodifiableList());

        return destinations.stream()
            .map(address -> BasicFileWriterV2.create(time, address))
            .collect(toUnmodifiableList());
    }

    public List<BasicFileWriterV2> createAll(LocalDateTime time){
        return create(time, addresses);
    }
}
