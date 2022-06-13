package server.v4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.Address;
import static server.v4.Usage.FILE;
import static server.v4.Usage.SOCKET;

public class MessageWriter {
    private final Map<Address, Map<Usage, WriterV4>> destinations = new HashMap<>();

    public void addAddress(@NonNull Address address, @NonNull WriterV4 writer){
        destinations.computeIfAbsent(address, k-> new HashMap<>());

        Map<Usage, WriterV4> usageWriterV4Map = destinations.get(address);
        if(writer instanceof BasicWriter){
            usageWriterV4Map.put(SOCKET, writer);
            return;
        }
        usageWriterV4Map.put(FILE, writer);
    }

    public void write(@NonNull Usage usage, @NonNull Message message, @NonNull List<Address> addresses){
        addresses.stream()
            .filter(destinations::containsKey)
            .map(address -> destinations.get(address))
            .filter(value -> value.containsKey(usage))
            .map(value -> value.get(usage))
            .forEach(writerV4 -> writerV4.write(message.create()));
    }

    public void writeAll(@NonNull Usage usage, @NonNull Message message){
        List<Address> addresses = destinations.keySet().stream().collect(Collectors.toUnmodifiableList());

        write(usage, message, addresses);
    }
}
