package server.v5;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;
import util.IoUtil;

/**
 * destination 에 연관된 outputstream 에 message 를 write 하는 역할.
 */
public class MessageWriter {
    private final Map<Destination, BufferedWriter> outputStreamMap;

    public MessageWriter(){
        this.outputStreamMap = new HashMap<>();
    }
    public MessageWriter(@NonNull Map<Destination, BufferedWriter> outputStreamMap) {
        this.outputStreamMap = outputStreamMap;
    }

    public static MessageWriter of(@NonNull Map<Destination, OutputStream> outputStreamMap){
        Map<Destination, BufferedWriter> collect = outputStreamMap
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> IoUtil.createWriter(e.getValue())));

        return new MessageWriter(collect);
    }

    public void addDestination(@NonNull Destination destination, @NonNull OutputStream outputStream){
        BufferedWriter out = IoUtil.createWriter(outputStream);

        outputStreamMap.put(destination, out);
    }

    public void write(@NonNull Destination destination, @NonNull Message message){
        BufferedWriter out = outputStreamMap.get(destination);

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
