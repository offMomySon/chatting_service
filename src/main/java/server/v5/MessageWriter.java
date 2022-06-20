package server.v5;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import util.IoUtil;

public class MessageWriter {
    private final Map<Destination, OutputStream> outputStreamMap = new HashMap<>();

    public void addAddressDirection(@NonNull Destination destination, @NonNull OutputStream outputStream){
        outputStreamMap.put(destination, outputStream);
    }

    public void write(@NonNull Destination destination, @NonNull Message message){
        BufferedWriter out = IoUtil.createWriter(outputStreamMap.get(destination));

        try {
            out.write(message.create());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeAll(@NonNull Message message){
        outputStreamMap.keySet()
            .forEach(out -> write(out, message));
    }
}
