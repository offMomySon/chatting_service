package server.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    /**
     * 역할.
     * 가지고있는 destination 에서 조건으로 필터링한 destination 들에 대해 write 를 수행한다.
     *
     * 사용성.
     * 조건은 predicate 로 받기 때문에, 외부에서 조건을 부여의 책임이 생긴다.
     * 외부에서 어떤것을 필터링하는지 알기 어렵다. ( destination 이 사용자측 문맥에 드러나지 않기 때문에 )
     */
    public void write(@NonNull Message message, @NonNull Predicate<Destination> destinationPredicate){
        List<Destination> destinations = outputStreamMap.keySet().stream()
            .filter(destinationPredicate)
            .collect(Collectors.toUnmodifiableList());

        destinations
            .forEach(destination -> write(message, destination));
    }

    /**
     * 역할.
     * function 에 따라 새롭게 생성된 destinations 에 write 를 수행하는 역할.
     *
     * 사용성.
     * Destinations 객체로 어떤것을 필터링하는지 외부에 명시가 가능해졌다.
     * 또한 destination 필터링 책임, destinations 변환책임 을 외부에 부여하였다.
     */
    public void write(@NonNull Message message, @NonNull Function<Destinations, Destinations> destinationsFunction){
        Destinations findDestinations = destinationsFunction.apply(new Destinations(outputStreamMap.keySet()));

        findDestinations
            .forEach(destination -> write(message,destination) );
    }
}
