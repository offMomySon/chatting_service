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
import util.IoUtil;
import static server.v5.Usage.FILE;

class MessageIpWriteStrategyTest {
    @DisplayName("지정한 ip 에 대해 message 를 출력합니다.")
    @ParameterizedTest
    @MethodSource("provideSourceAndDestination")
    void test2(Map<Destination, OutputStream> sourceMap, List<Destination> destinations){
        //given
        Message message = new FileMessage(LocalDateTime.now(), MessageOwner.INFO, "this is message.");

        MessageWriter messageWriter = MessageWriter.of(sourceMap);
        MessageIpWriteStrategy messageWriteStrategy = new MessageIpWriteStrategy(messageWriter,destinations);

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
        List<Destination> sourceDestination = List.of(
            new Destination(new Address("127.0.0.0"),FILE),
            new Destination(new Address("127.0.0.1"),FILE),
            new Destination(new Address("127.0.0.2"),FILE),
            new Destination(new Address("127.0.0.3"),FILE)
        );
        List<OutputStream> sourceOutputStreams = List.of(
            new ByteArrayOutputStream(),
            new ByteArrayOutputStream(),
            new ByteArrayOutputStream(),
            new ByteArrayOutputStream()
        );

        Map<Destination, OutputStream> sourceMap = new HashMap<>();
        IntStream.range(0, sourceDestination.size()).forEach(i-> sourceMap.put(sourceDestination.get(i), sourceOutputStreams.get(i)));

        List<Destination> destinations = List.of(
            new Destination(new Address("127.0.0.0"),FILE),
            new Destination(new Address("127.0.0.1"),FILE)
        );

        return Stream.of(Arguments.of(sourceMap, destinations));
    }
}