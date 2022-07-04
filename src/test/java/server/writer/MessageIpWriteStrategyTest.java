package server.writer;

import common.MessageOwner;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import server.Address;
import server.message.file.FileMessage;
import server.v5.Destination;
import server.v5.Message;
import server.v5.MessageWriter;
import server.v5.Usage;
import util.IoUtil;
import static server.v5.Usage.FILE;

class MessageIpWriteStrategyTest {
    @DisplayName("지정한 address 에 대해 message 를 출력합니다.")
    @ParameterizedTest
    @MethodSource("provideSourceAndDestination")
    void test2(Map<Address, OutputStream> sourceMap, Usage usage, List<Address> addresses){
        //given
        Message message = new FileMessage(LocalDateTime.now(), MessageOwner.INFO, "this is message.");

        MessageWriter messageWriter = MessageWriter.of(sourceMap, usage);
        MessageIpWriteStrategy messageWriteStrategy = new MessageIpWriteStrategy(messageWriter,addresses);

        //when
        messageWriteStrategy.write(message, usage);

        List<String> actualMessage = new LinkedList<>();
        for(Address address : addresses){
            actualMessage.add(sourceMap.get(address).toString());
        }

        //then
        Assertions.assertThat(actualMessage).allSatisfy(actual->{
            Assertions.assertThat(actual).isEqualTo(message.create());
        });
    }

    private static Stream<Arguments> provideSourceAndDestination(){
        List<Address> sourceDestination = List.of(
            new Address("127.0.0.0"),
            new Address("127.0.0.1"),
            new Address("127.0.0.2"),
            new Address("127.0.0.3")
        );
        List<OutputStream> sourceOutputStreams = List.of(
            new ByteArrayOutputStream(),
            new ByteArrayOutputStream(),
            new ByteArrayOutputStream(),
            new ByteArrayOutputStream()
        );

        Map<Address, OutputStream> sourceMap = new HashMap<>();
        IntStream.range(0, sourceDestination.size()).forEach(i-> sourceMap.put(sourceDestination.get(i), sourceOutputStreams.get(i)));

        List<Address> destinations = List.of(
            new Address("127.0.0.0"),
            new Address("127.0.0.1")
        );

        return Stream.of(Arguments.of(sourceMap, FILE, destinations));
    }
}