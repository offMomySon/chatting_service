package server.v5;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.Address;
import util.IoUtil;

/**
 * 역할.
 * destination 에 message 를 쓰는 역할.
 *
 * destination 은 사용처와 주소의 조합으로 출력해야하는 목적지를 가리킨다.
 */
public class MessageWriter {
    private final Map<Destination, BufferedWriter> outputStreamMap;

    public MessageWriter(){
        this.outputStreamMap = new HashMap<>();
    }
    private MessageWriter(@NonNull Map<Destination, BufferedWriter> outputStreamMap) {
        this.outputStreamMap = outputStreamMap;
    }

    public static MessageWriter of(Map<Destination, OutputStream> sourceMap) {
        Map<Destination, BufferedWriter> outputStreamMap = sourceMap
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e-> IoUtil.createWriter(e.getValue())));

        return new MessageWriter(outputStreamMap);
    }

    public void addDestination(@NonNull Destination destination, @NonNull OutputStream outputStream){
        BufferedWriter out = IoUtil.createWriter(outputStream);

        outputStreamMap.put(destination, out);
    }

    public void removeDestination(@NonNull Destination destination){
        outputStreamMap.remove(destination);
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

    public void writeAll(@NonNull Message message, @NonNull Usage usage){
        outputStreamMap.keySet().stream()
            .filter(k -> k.getUsage() == usage )
            .forEach(out -> write(out, message));
    }
}
