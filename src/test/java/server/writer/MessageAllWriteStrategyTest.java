package server.writer;

import common.MessageOwner;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import server.Address;
import server.message.file.LogMessage;
import server.v5.Destination;
import server.v5.Message;
import server.v5.MessageWriter;
import server.v5.Usage;
import static server.v5.Usage.FILE;

class MessageAllWriteStrategyTest {
    @DisplayName("모든 destination 으로 message 를 출력합니다.")
    @ParameterizedTest
    @MethodSource("provideSourceMap")
    void test2(Map<Destination, OutputStream> sourceMap, Usage usage){
        //given
        Message message = LogMessage.of(LocalDateTime.now(), MessageOwner.INFO, "this is message.");

        MessageWriter messageWriter = MessageWriter.of(sourceMap);
        MessageAllWriteStrategy messageWriteStrategy = new MessageAllWriteStrategy(messageWriter, usage);

        //when
        messageWriteStrategy.write(message);

        List<String> actualMessage = new LinkedList<>();
        sourceMap.values().forEach( out -> actualMessage.add(out.toString()));

        //then
        Assertions.assertThat(actualMessage).allSatisfy(actual->{
            Assertions.assertThat(actual).isEqualTo(message.create());
        });
    }

    private static Stream<Arguments> provideSourceMap(){
        Usage usage = FILE;

        List<Destination> sourceDestination = List.of(
            new Destination(new Address("127.0.0.0"), usage),
            new Destination(new Address("127.0.0.1"), usage),
            new Destination(new Address("127.0.0.2"), usage),
            new Destination(new Address("127.0.0.3"), usage)
        );
        List<OutputStream> sourceOutputStreams = List.of(
            new ByteArrayOutputStream(),
            new ByteArrayOutputStream(),
            new ByteArrayOutputStream(),
            new ByteArrayOutputStream()
        );

        Map<Destination, OutputStream> sourceMap = new HashMap<>();
        IntStream.range(0, sourceDestination.size()).forEach(i-> sourceMap.put(sourceDestination.get(i), sourceOutputStreams.get(i)));

        return Stream.of(Arguments.of(sourceMap, usage));
    }
}