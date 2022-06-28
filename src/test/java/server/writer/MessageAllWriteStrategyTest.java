package server.writer;

import common.MessageOwner;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.Address;
import server.message.file.FileMessage;
import server.v5.Destination;
import server.v5.Message;
import server.v5.MessageWriter;
import static server.v5.Usage.FILE;

class MessageAllWriteStrategyTest {
    @DisplayName("모든 ip 에 대해 생성한 message 를 전달해야합니다.")
    @Test
    void test2(){
        //given
        List<Address> addresses = List.of(new Address("127.0.0.0"), new Address("127.0.0.1"), new Address("127.0.0.2"), new Address("127.0.0.3"));
        List<Destination> destinations = addresses.stream().map(address -> new Destination(address, FILE)).collect(Collectors.toUnmodifiableList());

        List<ByteArrayOutputStream> outputStreams = List.of(new ByteArrayOutputStream(), new ByteArrayOutputStream(), new ByteArrayOutputStream(), new ByteArrayOutputStream());
        Map<Destination, OutputStream> outputStreamMap = new HashMap<>();
        for (int i = 0; i < destinations.size() ; i++) {
            outputStreamMap.put(destinations.get(i), outputStreams.get(i));
        }

        MessageWriter messageWriter = new MessageWriter();
        outputStreamMap.forEach(messageWriter::addDestination);

        MessageAllWriteStrategy messageWriteStrategy = new MessageAllWriteStrategy(messageWriter);
        Message message = new FileMessage(LocalDateTime.now(), MessageOwner.INFO, "this is message.");

        //when
        messageWriteStrategy.write(FILE, message);

        //then
        for(ByteArrayOutputStream outputStream : outputStreams) {
            Assertions.assertThat(outputStream.toString()).isEqualTo(message.create());
        }
    }
}