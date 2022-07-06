package server.writer;

import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.Address;
import server.v5.Destination;
import server.v5.Message;
import server.v5.MessageWriter;
import server.v5.Usage;

/**
 * 역할.
 * 지정한 destination 에 message 를 쓰는 역할.
 *
 * 아래의 구조의 메서드는 목적지가 destination 이 아니라,
 * 지정한 address 에 대해 usage 에 맞는 message 를 쓴다의 범용적 기능의 역할.
 * public void write(@NonNull Message message, @NonNull Usage usage)
 *
 * 즉, instance 에 따라 적절한 행위를 수행하는 시스템 도메인이 아니라,
 * 지정한 address 에 대해 message, usage 를 이용해 모든 타입의 메세지를 출력하는
 * 범용적 기능을 가진 객체 였다.
 * -> 도메인 관심사가 흩어진다. ( destination 에 message 를 쓰는 것이 아니라. address 와 usage 의 조합한 destination 에 message 를 쓴다. 가 됨.)
 */
public class MessageDestinationWriteStrategy implements MessageWriteStrategy{
    private final MessageWriter messageWriter;
    private final List<Destination> destinations;

    public MessageDestinationWriteStrategy(@NonNull MessageWriter messageWriter, @NonNull List<Destination> destinations) {
        this.messageWriter = messageWriter;
        this.destinations = destinations;
    }

    public static MessageDestinationWriteStrategy from(@NonNull MessageWriter messageWriter, @NonNull List<Address> addresses, @NonNull Usage usage){
        List<Destination> destinations = addresses.stream().map(address -> new Destination(address, usage)).collect(Collectors.toUnmodifiableList());

        return new MessageDestinationWriteStrategy(messageWriter, destinations);
    }

    @Override
    public void write(@NonNull Message message) {
        destinations.forEach(destination -> messageWriter.write(destination, message));
    }
}
