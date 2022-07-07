package server.v5;

import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import server.Address;
import static org.junit.jupiter.api.Assertions.*;

class DestinationTest {

    @DisplayName("동일한 address, usage 를 가지고 있으면 equal 비교시 true 를 반환합니다.")
    @ParameterizedTest
    @CsvSource(value = {"127.0.0.1, file", "127.0.0.2, file", "127.0.0.3, file", "127.0.0.1, socket", "127.0.0.2, socket", "127.1.12.5, socket"})
    void test1(String sAddress, String sUsage) {
        //given
        Destination destination1 = new Destination(new Address(sAddress), Usage.find(sUsage).orElseThrow(()-> new RuntimeException("not exist usage")));
        Destination destination2 = new Destination(new Address(sAddress), Usage.find(sUsage).orElseThrow(()-> new RuntimeException("not exist usage")));

        //when
        boolean actual = Objects.equals(destination1, destination2);

        //then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("동일한 address, usage 를 가지고 있으면 hashcode 비교시 true 를 반환합니다.")
    @ParameterizedTest
    @CsvSource(value = {"127.0.0.1, file", "127.0.0.2, file", "127.0.0.3, file", "127.0.0.1, socket", "127.0.0.2, socket", "127.1.12.5, socket"})
    void test2(String sAddress, String sUsage) {
        //given
        Destination destination1 = new Destination(new Address(sAddress), Usage.find(sUsage).orElseThrow(()-> new RuntimeException("not exist usage")));
        Destination destination2 = new Destination(new Address(sAddress), Usage.find(sUsage).orElseThrow(()-> new RuntimeException("not exist usage")));

        //when
        boolean actual = Objects.equals(destination1.hashCode(), destination2.hashCode());

        //then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("address, usage 가 동일하지 않으면 equal 비교시 false 를 반환합니다.")
    @ParameterizedTest
    @CsvSource(value = {"127.0.0.1, file, 127.0.0.1, SOCKET", "127.0.0.1, FILE, 127.0.0.2, file", "127.1.1.1, SOCKET, 127.1.1.2, SOCKET"})
    void test3(String sAddress1, String sUsage1, String sAddress2, String sUsage2){
        //given
        Destination destination1 = new Destination(new Address(sAddress1), Usage.find(sUsage1).orElseThrow(()-> new RuntimeException("not exist usage")));
        Destination destination2 = new Destination(new Address(sAddress2), Usage.find(sUsage2).orElseThrow(()-> new RuntimeException("not exist usage")));

        //when
        boolean actual = Objects.equals(destination1, destination2);

        //then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("address, usage 가 동일하지 않으면 hashcode 비교시 false 를 반환합니다.")
    @ParameterizedTest
    @CsvSource(value = {"127.0.0.1, file, 127.0.0.1, SOCKET", "127.0.0.1, FILE, 127.0.0.2, file", "127.1.1.1, SOCKET, 127.1.1.2, SOCKET"})
    void test4(String sAddress1, String sUsage1, String sAddress2, String sUsage2){
        //given
        Destination destination1 = new Destination(new Address(sAddress1), Usage.find(sUsage1).orElseThrow(()-> new RuntimeException("not exist usage")));
        Destination destination2 = new Destination(new Address(sAddress2), Usage.find(sUsage2).orElseThrow(()-> new RuntimeException("not exist usage")));

        //when
        boolean actual = Objects.equals(destination1.hashCode(), destination2.hashCode());

        //then
        Assertions.assertThat(actual).isFalse();
    }
}