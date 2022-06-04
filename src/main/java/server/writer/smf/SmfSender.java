package server.writer.smf;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.Address;
import server.message.SimpleMessageFormat;
import static util.IoUtil.createWriter;

public class SmfSender {
    private final Map<Address, BufferedWriter> destinations = new HashMap<>();

    public void addAddress(@NonNull Address address, @NonNull OutputStream outputStream){
        BufferedWriter bufferedWriter = createWriter(outputStream);

        destinations.put(address, bufferedWriter);
    }

    public void send(@NonNull SimpleMessageFormat smf, List<Address> addresses){
        String message = smf.createMessage();

        addresses.stream()
            .map(destinations::get)
            .forEach(out -> doSend(message, out));
    }

    public void sendAll(@NonNull SimpleMessageFormat smf){
        List<Address> addresses = destinations.keySet().stream().collect(Collectors.toUnmodifiableList());

        send(smf, addresses);
    }

    private static void doSend(String message, BufferedWriter out){
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
