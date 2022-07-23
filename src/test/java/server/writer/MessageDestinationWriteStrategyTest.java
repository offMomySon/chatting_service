package server.writer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import server.Address;
import server.message.file.LogInfoMessage;
import server.message.Message;
import static server.writer.Usage.FILE;

/**
 * 1)
 * destination 에 message 를 쓰기 위함인데,
 * 아래의 코드에서 addresses 를 기반으로 객체를 생성하는것에 이상함을 느꼈다.
 * MessageIpWriteStrategy messageWriteStrategy = new MessageIpWriteStrategy(messageWriter, addresses);
 *
 * 또한 MessageWriter 생성에도 실제로는 destination 이 필요한데,
 * address, usage 를 넘겨줘야한다는 부분에서 이상함을 느꼈다.
 * MessageWriter messageWriter = MessageWriter.of(sourceMap, usage);
 *
 * 2)
 * destination 에 메세지를 쓰는 것이기 때문에, address 대신 destination 으로 생성하는게 맞다고 생각함.
 * 객체생성을 destination 으로 변경하였다.
 *
 * 3)
 * [1] 테스트가 쉬워짐.
 * 테스트 param 에서 usage 가 필요없어짐.
 *
 * [2] MessageWriter 객체 생성이 한번에 이루어짐.
 * 생성자에서 address, usage 를 받아 내부에서 변환하는 작업이 필요없어짐.
 *
 * [3] MessageIpWriteStrategy 에서 destination 을 관리함에 따라
 * write 메서드에서 어떤 usage 를 받아야할지에 대한 고민이 사라짐. ( 적절한 message 를 던져주면 된다.)
 *
 *
 * 키워드
 * 범용적인 기능의 객체.
 * 시스템 도메인 객체.
 *
 */
class MessageDestinationWriteStrategyTest {
    @DisplayName("지정한 destination 으로 message 를 출력합니다.")
    @ParameterizedTest
    @MethodSource("provideSourceAndDestination")
    void test2(Map<Destination, OutputStream> sourceMap, List<Destination> destinations){
        //given
        Message message = LogInfoMessage.ofCurrent("this is message.");

        MessageWriter messageWriter = MessageWriter.of(sourceMap);
        MessageDestinationWriteStrategy messageWriteStrategy = new MessageDestinationWriteStrategy(messageWriter, destinations);

        //when
        messageWriteStrategy.write(message);

        List<String> actualMessage = new LinkedList<>();
        for(Destination destination : destinations){
            actualMessage.add(sourceMap.get(destination).toString());
        }

        //then
        Assertions.assertThat(actualMessage).allSatisfy(actual->{
            Assertions.assertThat(actual).isEqualTo(message.create());
        });
    }

    private static Stream<Arguments> provideSourceAndDestination(){
        Usage usage = FILE;

        List<Destination> sourceDestination = List.of(
            new Destination(new Address("127.0.0.0"), usage),
            new Destination(new Address("127.0.0.1"), usage),
            new Destination(new Address("127.0.0.2"), usage),
            new Destination(new Address("127.0.0.3"), usage)
        );
        Map<Destination, OutputStream> sourceMap = new HashMap<>();
        sourceDestination.forEach(destination -> sourceMap.put(destination, new ByteArrayOutputStream()));

        List<Destination> destinations = List.of(
            new Destination(new Address("127.0.0.0"), usage),
            new Destination(new Address("127.0.0.1"), usage)
        );

        return Stream.of(Arguments.of(sourceMap, destinations));
    }
}