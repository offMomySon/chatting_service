package server.v5;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import util.IoUtil;

public class MessageWriter {
    private final Map<AddressDirection, OutputStream> outputStreamMap = new HashMap<>();

    public void addAddressDirection(@NonNull AddressDirection addressDirection, @NonNull OutputStream outputStream){
        outputStreamMap.put(addressDirection, outputStream);
    }

    public void write(@NonNull AddressDirection addressDirection, @NonNull Message message){
        BufferedWriter out = IoUtil.createWriter(outputStreamMap.get(addressDirection));

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
