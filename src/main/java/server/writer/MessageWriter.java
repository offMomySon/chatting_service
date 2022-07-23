package server.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.message.Message;
import util.IoUtil;

/**
 * 역할.
 * destination 에 message 를 쓰는 역할.
 * destination 은 사용처와 주소의 조합으로 출력해야하는 목적지를 가리킨다.
 */

/**
 * writeAll 의 param 을 변경한다.
 * 이유는 MessageWriter 의 역할이 destination 에 message 를 보내는 역할이다.
 * 그런데 writeAll 은 destination 이 아닌 usage 를 받아서 처리하고 있다.
 * 기존에 설정한 'destination 에 message 를 보낸다.'의 역할이 아니게 된다. 이는 client 의 사용성 측면에서도 이상함을 느끼게 한다.
 * 그렇기에 writeAll 의 param 을 predicate 로 변형시킨다.
 *
 * 또한 모든 destination 에 메세지를 쓰는것이 아니라 조건에 따라 쓰는것이기 때문에
 * writeAll 이 아닌 write 를 쓴다.
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

    public void write(@NonNull Message message, @NonNull Destination destination){
        BufferedWriter out = outputStreamMap.get(destination);

        try {
            out.write(message.create());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(@NonNull Message message, @NonNull Predicate<Destination> destinationPredicate){
        List<Destination> destinations = outputStreamMap.keySet().stream()
            .filter(destinationPredicate)
            .collect(Collectors.toUnmodifiableList());

        destinations.stream()
            .filter(outputStreamMap::containsKey)
            .forEach(destination -> write(message, destination));
    }
}
