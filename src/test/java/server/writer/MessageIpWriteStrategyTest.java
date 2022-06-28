package server.writer;

import common.MessageOwner;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.Address;
import server.message.file.FileMessage;
import server.v5.Destination;
import server.v5.Message;
import server.v5.MessageWriter;
import server.v5.Usage;
import static server.v5.Usage.FILE;

class MessageIpWriteStrategyTest {
    @DisplayName("test")
    @Test
    void test2(){
        //given
        List<Address> addresses = List.of(new Address("127.0.0.0"), new Address("127.0.0.1"), new Address("127.0.0.2"), new Address("127.0.0.3"));
        List<ByteArrayOutputStream> outputStreams = List.of(new ByteArrayOutputStream(), new ByteArrayOutputStream(), new ByteArrayOutputStream(), new ByteArrayOutputStream());
        List<Destination> destinations = new LinkedList<>();
        for(Address address : addresses){
            destinations.add(new Destination(address, FILE));
        }
        Map<Destination, OutputStream> outputStreamMap = new HashMap<>();
        for (int i = 0; i < destinations.size() ; i++) {
            outputStreamMap.put(destinations.get(i), outputStreams.get(i));
        }

        MessageWriter messageWriter = new MessageWriter();
        outputStreamMap.forEach(messageWriter::addDestination);

        MessageIpWriteStrategy messageIpWriteStrategy = new MessageIpWriteStrategy(messageWriter, addresses);
        Message message = new FileMessage(LocalDateTime.now(), MessageOwner.INFO, "this is message.");

        //when
        messageIpWriteStrategy.write(FILE, message);

        //then
        for(ByteArrayOutputStream outputStream : outputStreams) {
            Assertions.assertThat(outputStream.toString()).isEqualTo(message.create());
        }
    }
}