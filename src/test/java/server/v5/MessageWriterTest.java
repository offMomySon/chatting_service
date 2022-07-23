package server.v5;

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
import server.message.file.LogServerMessage;
import server.writer.MessageWriter;
import static server.v5.Usage.FILE;

class MessageWriterTest {

    @DisplayName("지정한 destination 으로 message 를 출력합니다.")
    @ParameterizedTest
    @MethodSource("provideSourceMapAndDestinations")
    void test(Map<Destination, OutputStream> sourceMap, List<Destination> destinations){
        //given
        Message message = LogServerMessage.ofCurrent("this is test message.");
        MessageWriter messageWriter = MessageWriter.of(sourceMap);

        //when
        for(Destination destination: destinations){
            messageWriter.write(message, destination);
        }

        List<String> actualMessage = new LinkedList<>();
        for(Destination destination: destinations){
            actualMessage.add(sourceMap.get(destination).toString());
        }

        //then
        Assertions.assertThat(actualMessage).allSatisfy(actual->{
            Assertions.assertThat(actual).isEqualTo(message.create());
        });
    }

    @DisplayName("모든 destination 으로 message 를 출력합니다.")
    @ParameterizedTest
    @MethodSource("provideSourceMapAndUsage")
    void test1(Map<Destination, OutputStream> sourceMap, Usage usage){
        //given
        Message message = LogInfoMessage.ofCurrent("this is test message.");
        MessageWriter messageWriter = MessageWriter.of(sourceMap);

        //when
        messageWriter.write(message, d-> d.getUsage() == usage);

        List<String> actualMessage = new LinkedList<>();
        for(OutputStream outputStream : sourceMap.values()){
            actualMessage.add(outputStream.toString());
        }

        //then
        Assertions.assertThat(actualMessage).allSatisfy(actual -> {
           Assertions.assertThat(actual).isEqualTo(message.create());
        });
    }

    private static Stream<Arguments> provideSourceMapAndDestinations() {
        List<Destination> destinations = List.of(
            new Destination(new Address("127.0.0.1"), FILE),
            new Destination(new Address("127.0.0.2"), FILE),
            new Destination(new Address("127.0.0.3"), FILE),
            new Destination(new Address("127.0.0.4"), FILE)
        );

        Map<Destination, OutputStream> sourceMap = new HashMap<>();
        destinations.forEach( destination -> sourceMap.put(destination, new ByteArrayOutputStream()));

        return Stream.of(Arguments.of(sourceMap, destinations));
    }

    public static Stream<Arguments> provideSourceMapAndUsage() {
        Usage usage = FILE;

        List<Destination> destinations = List.of(
            new Destination(new Address("127.0.0.1"), usage),
            new Destination(new Address("127.0.0.2"), usage),
            new Destination(new Address("127.0.0.3"), usage),
            new Destination(new Address("127.0.0.4"), usage)
        );

        Map<Destination, OutputStream> sourceMap = new HashMap<>();
        destinations.forEach( destination -> sourceMap.put(destination, new ByteArrayOutputStream()));

        return Stream.of(Arguments.of(sourceMap, usage));
    }
}