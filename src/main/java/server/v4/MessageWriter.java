package server.v4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.Address;
import server.v4.message.file.FileMessageV4;
import server.v4.message.smf.SimpleMessageFormatV4;
import static server.v4.Usage.FILE;
import static server.v4.Usage.SOCKET;

public class MessageWriter {
    private final Map<Address, Map<Usage, WriterV4>> destinations = new HashMap<>();

    public void addAddress(@NonNull Address address, @NonNull WriterV4 writer){
        destinations.computeIfAbsent(address, k-> new HashMap<>());

        Map<Usage, WriterV4> usageWriterV4Map = destinations.get(address);
        if(writer instanceof SimpleFormatMessageWriter){
            usageWriterV4Map.put(SOCKET, writer);
            return;
        }
        if(writer instanceof  TimeAddressNamedFileWriter){
            usageWriterV4Map.put(FILE, writer);
            return;
        }

        throw new RuntimeException("not typed writer.");
    }

    public void write(@NonNull SimpleMessageFormatV4 message, List<Address> addresses){
        write(SOCKET, message, addresses);
    }

    public void writeAll(@NonNull SimpleMessageFormatV4 message){
        writeAll(SOCKET, message);
    }

    public void write(@NonNull FileMessageV4 message, List<Address> addresses){
        write(FILE, message, addresses);
    }

    public void writeAll(@NonNull FileMessageV4 message){
        writeAll(FILE, message);
    }

    private void write(@NonNull Usage usage, @NonNull Message message, @NonNull List<Address> addresses){

        addresses.stream()
            .filter(destinations::containsKey)
            .map(destinations::get)
            .filter(usages -> usages.containsKey(usage))
            .map(usages -> usages.get(usage))
            .forEach(writerV4 -> writerV4.write(message.createMessage()));
    }

    private void writeAll(@NonNull Usage usage, @NonNull Message message){
        List<Address> allAddresses = destinations.keySet().stream().collect(Collectors.toUnmodifiableList());

        write(usage, message, allAddresses);
    }
}
